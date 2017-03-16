package com.forms.beneform4j.excel.core.imports.stream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.RKRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.StringRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.springframework.core.io.Resource;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.ExcelUtils;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Excel对象流式解析工具类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class WorkbookStreamUtils {

    /**
     * 解析Excel文件
     * 
     * @param resource Excel文件
     * @param handler 回调处理器
     */
    public static void parse(Resource resource, IWorkbookStreamHandler handler) {
        File file = null;
        try {
            file = resource.getFile();
        } catch (IOException e1) {
            parseResourceStream(resource, handler);
            return;
        }

        try {
            if (ExcelUtils.isXlsx2007(resource.getFilename(), true)) {
                XLSX2CSV xlsx2csv = new XLSX2CSV(OPCPackage.open(file), handler);
                xlsx2csv.process();
                return;
            } else {
                XLS2CSVmra xls2csv = new XLS2CSVmra(new POIFSFileSystem(file), handler);
                xls2csv.process();
                return;
            }
        } catch (Exception e) {
            Throw.throwRuntimeException(e);
        }
    }

    /**
     * 解析Excel文件
     * 
     * @param location Excel文件
     * @param handler 回调处理器
     */
    public static void parse(String location, IWorkbookStreamHandler handler) {
        Resource resource = CoreUtils.getResource(location);
        parse(resource, handler);
    }

    /**
     * 解析Excel文件流
     * 
     * @param input Excel文件输入流
     * @param handler 回调处理器
     */
    public static void parse(InputStream input, IWorkbookStreamHandler handler) {
        try {
            if (ExcelUtils.isXlsx2007(input)) {
                XLSX2CSV xlsx2csv = new XLSX2CSV(OPCPackage.open(input), handler);
                xlsx2csv.process();
            } else {
                XLS2CSVmra xls2csv = new XLS2CSVmra(new POIFSFileSystem(input), handler);
                xls2csv.process();
            }
        } catch (IOException ie) {
            Throw.throwRuntimeException("文件读写异常", ie);
        } catch (Exception e) {
            Throw.throwRuntimeException("解析Excel文件时出现异常", e);
        }
    }

    private static void parseResourceStream(Resource resource, IWorkbookStreamHandler handler) {
        InputStream input = null;
        try {
            input = new BufferedInputStream(resource.getInputStream());
            parse(input, handler);
        } catch (IOException ie) {
            Throw.throwRuntimeException("文件读写异常", ie);
        } finally {
            CoreUtils.closeQuietly(input);
        }
    }

    private static class ProcessFinishException extends RuntimeException {
        private static final long serialVersionUID = 4592810075480294546L;
    }

    // 2003版本解析
    private static class XLS2CSVmra implements HSSFListener {

        private final POIFSFileSystem fs;
        private final IWorkbookStreamHandler handler;
        private final List<String> cells = new ArrayList<String>();
        private boolean outputFormulaValues = true;
        private SheetRecordCollectingListener workbookBuildingListener;
        private HSSFWorkbook stubWorkbook;
        private SSTRecord sstRecord;
        private FormatTrackingHSSFListener formatListener;
        private int sheetIndex = -1;
        private String sheetName;
        private BoundSheetRecord[] orderedBSRs;
        private List<BoundSheetRecord> boundSheetRecords = new ArrayList<BoundSheetRecord>();
        private boolean outputNextStringRecord;

        public XLS2CSVmra(POIFSFileSystem fs, IWorkbookStreamHandler handler) {
            this.fs = fs;
            this.handler = handler;
        }

        public void process() throws IOException {
            try {
                handler.initialize();
                MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
                formatListener = new FormatTrackingHSSFListener(listener);

                HSSFEventFactory factory = new HSSFEventFactory();
                HSSFRequest request = new HSSFRequest();

                if (outputFormulaValues) {
                    request.addListenerForAllRecords(formatListener);
                } else {
                    workbookBuildingListener = new SheetRecordCollectingListener(formatListener);
                    request.addListenerForAllRecords(workbookBuildingListener);
                }

                try {
                    factory.processWorkbookEvents(request, fs);
                } catch (ProcessFinishException e) {
                }
            } finally {
                handler.end();
            }
        }

        @Override
        public void processRecord(Record record) {
            String thisStr = null;
            boolean isDefault = false;

            switch (record.getSid()) {
                case BoundSheetRecord.sid:
                    boundSheetRecords.add((BoundSheetRecord) record);
                    break;
                case BOFRecord.sid:
                    BOFRecord br = (BOFRecord) record;
                    if (br.getType() == BOFRecord.TYPE_WORKSHEET) {
                        if (sheetIndex >= 0) {
                            if (!handler.endSheet(sheetIndex, sheetName)) {//开始解析一个Sheet
                                throw new ProcessFinishException();
                            }
                        }
                        // Create sub workbook if required
                        if (workbookBuildingListener != null && stubWorkbook == null) {
                            stubWorkbook = workbookBuildingListener.getStubHSSFWorkbook();
                        }
                        sheetIndex++;
                        if (orderedBSRs == null) {
                            orderedBSRs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);
                        }
                        sheetName = orderedBSRs[sheetIndex].getSheetname();
                        if (!handler.startSheet(sheetIndex, sheetName)) {
                            throw new ProcessFinishException();
                        }
                    }
                    break;

                case SSTRecord.sid:
                    sstRecord = (SSTRecord) record;
                    break;

                case BlankRecord.sid:
                    isDefault = true;
                    break;
                case BoolErrRecord.sid:
                    isDefault = true;
                    break;

                case FormulaRecord.sid:
                    FormulaRecord frec = (FormulaRecord) record;
                    if (outputFormulaValues) {
                        if (Double.isNaN(frec.getValue())) {
                            outputNextStringRecord = true;
                        } else {
                            thisStr = formatListener.formatNumberDateCell(frec);
                        }
                    } else {
                        thisStr = HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedExpression());
                    }
                    break;
                case StringRecord.sid:
                    if (outputNextStringRecord) {
                        StringRecord srec = (StringRecord) record;
                        thisStr = srec.getString();
                        outputNextStringRecord = false;
                    }
                    break;

                case LabelRecord.sid:
                    LabelRecord lrec = (LabelRecord) record;
                    thisStr = lrec.getValue();
                    break;
                case LabelSSTRecord.sid:
                    LabelSSTRecord lsrec = (LabelSSTRecord) record;
                    if (sstRecord == null) {
                        isDefault = true;
                    } else {
                        thisStr = sstRecord.getString(lsrec.getSSTIndex()).toString();
                    }
                    break;
                case NoteRecord.sid:
                    isDefault = true;
                    break;
                case NumberRecord.sid:
                    NumberRecord numrec = (NumberRecord) record;
                    thisStr = formatListener.formatNumberDateCell(numrec);
                    break;
                case RKRecord.sid:
                    isDefault = true;
                    break;
                default:
                    break;
            }

            if (record instanceof MissingCellDummyRecord) {
                isDefault = true;
            }

            if (isDefault) {
                cells.add(handler.getDefaultCellValue());
            } else if (thisStr != null) {
                cells.add(thisStr);
            }

            if (record instanceof LastCellOfRowDummyRecord) {
                try {
                    if (!handler.row(cells, ((LastCellOfRowDummyRecord) record).getRow())) {
                        throw new ProcessFinishException();
                    }
                } finally {
                    cells.clear();
                }
            }
        }
    }

    // 2007版本解析
    private static class XLSX2CSV {

        private static final ErrorHandler processFinishException = new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
                handleException(exception);
            }

            @Override
            public void error(SAXParseException exception) throws SAXException {
                handleException(exception);
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                handleException(exception);
            }

            private void handleException(SAXParseException exception) {
                if (exception.getCause() instanceof ProcessFinishException) {
                    throw (ProcessFinishException) exception.getCause();
                }
            }
        };

        private class ExtractSheetCallbackHandler implements SheetContentsHandler {
            private int currentRow = -1;
            private int currentCol = -1;

            private void handleRow(int rowNum) {
                try {
                    if (!handler.row(cells, rowNum)) {
                        throw new ProcessFinishException();
                    }
                } finally {
                    cells.clear();
                }
            }

            public void startRow(int rowNum) {
                for (int i = currentRow + 1; i < rowNum; i++) {
                    handleRow(i);
                }
                currentRow = rowNum;
                currentCol = -1;
            }

            public void endRow(int rowNum) {
                handleRow(rowNum);
            }

            public void cell(String cellReference, String formattedValue, XSSFComment comment) {
                if (cellReference == null) {
                    cellReference = new CellAddress(currentRow, currentCol).formatAsString();
                }

                CellReference reference = new CellReference(cellReference);
                int thisCol = reference.getCol();
                int missedCols = thisCol - currentCol - 1;
                for (int i = 0; i < missedCols; i++) {
                    cells.add(handler.getDefaultCellValue());
                }
                cells.add(formattedValue);
                currentCol = thisCol;
            }

            public void headerFooter(String text, boolean isHeader, String tagName) {}
        }

        private final OPCPackage xlsxPackage;
        private final IWorkbookStreamHandler handler;
        private final List<String> cells = new ArrayList<String>();

        public XLSX2CSV(OPCPackage pkg, IWorkbookStreamHandler handler) {
            this.xlsxPackage = pkg;
            this.handler = handler;
        }

        private boolean processSheet(StylesTable styles, ReadOnlySharedStringsTable strings, SheetContentsHandler sheetHandler, InputStream sheetInputStream) throws IOException, ParserConfigurationException, SAXException {
            DataFormatter formatter = new DataFormatter();
            InputSource sheetSource = new InputSource(sheetInputStream);
            try {
                XMLReader sheetParser = SAXHelper.newXMLReader();
                ContentHandler handler = new XSSFSheetXMLHandler(styles, null, strings, sheetHandler, formatter, false);
                sheetParser.setContentHandler(handler);
                sheetParser.setErrorHandler(processFinishException);
                sheetParser.parse(sheetSource);
            } catch (ProcessFinishException e) {
                return false;
            } catch (Exception e) {
                throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
            }
            return true;
        }

        public void process() throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
            try {
                handler.initialize();
                ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
                XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
                StylesTable styles = xssfReader.getStylesTable();
                XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
                int sheetIndex = 0;
                while (iter.hasNext()) {
                    InputStream stream = null;
                    try {
                        stream = iter.next();
                        String sheetName = iter.getSheetName();
                        // 开始处理当前表单
                        if (!handler.startSheet(sheetIndex, sheetName)) {
                            break;
                        }

                        // 继续处理表单
                        ExtractSheetCallbackHandler callbackHandler = new ExtractSheetCallbackHandler();
                        if (!processSheet(styles, strings, callbackHandler, stream)) {//完成
                            break;
                        }

                        // 结束当前表单的处理
                        if (!handler.endSheet(sheetIndex, sheetName)) {//完成
                            break;
                        }
                    } finally {
                        CoreUtils.closeQuietly(stream);
                    }
                }
            } finally {
                handler.end();
            }
        }
    }
}
