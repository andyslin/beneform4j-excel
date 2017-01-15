package com.forms.beneform4j.excel.core.imports.tocsv;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
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
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/* package */ class XLSX2CSV {
    /**
     * Uses the XSSF Event SAX helpers to do most of the work of parsing the Sheet XML, and outputs
     * the contents as a (basic) CSV.
     */
    private class SheetToCSV implements SheetContentsHandler {
        private boolean firstCellOfRow = false;
        private int currentRow = -1;
        private int currentCol = -1;

        private void outputMissingRows(int number) {
            for (int i = 0; i < number; i++) {
                rowCallback.callback(null, sheetName, sheetIndex, rowIndex++);
            }
        }

        public void startRow(int rowNum) {
            outputMissingRows(rowNum - currentRow - 1);
            cells = new ArrayList<String>();
            firstCellOfRow = true;
            currentRow = rowNum;
            currentCol = -1;
        }

        public void endRow(int rowNum) {
            rowCallback.callback(cells, sheetName, sheetIndex, rowIndex++);
        }

        public void cell(String cellReference, String formattedValue, XSSFComment comment) {
            if (firstCellOfRow) {
                firstCellOfRow = false;
            } else {
                //output.append(',');
            }

            // gracefully handle missing CellRef here in a similar way as XSSFCell does
            if (cellReference == null) {
                cellReference = new CellAddress(currentRow, currentCol).formatAsString();
            }

            // Did we miss any cells?
            int thisCol = (new CellReference(cellReference)).getCol();
            int missedCols = thisCol - currentCol - 1;
            for (int i = 0; i < missedCols; i++) {
                cells.add("");
            }
            currentCol = thisCol;

            cells.add(formattedValue);
        }

        public void headerFooter(String text, boolean isHeader, String tagName) {

        }
    }

    ///////////////////////////////////////

    private final OPCPackage xlsxPackage;

    private IRowCallback rowCallback; //回调

    private String sheetName;

    private int sheetIndex;

    private int rowIndex;

    private List<String> cells;

    /**
     * Creates a new XLSX -> CSV converter
     *
     * @param pkg The XLSX package to process
     * @param output The PrintStream to output the CSV to
     * @param minColumns The minimum number of columns to output, or -1 for no minimum
     */
    public XLSX2CSV(OPCPackage pkg, IRowCallback rowCallback) {
        this.xlsxPackage = pkg;
        this.rowCallback = rowCallback;
        if (null == rowCallback) {
            throw new IllegalArgumentException("the callback is empty.");
        }
    }

    /**
     * Parses and shows the content of one sheet using the specified styles and shared-strings
     * tables.
     *
     * @param styles
     * @param strings
     * @param sheetInputStream
     */
    public void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings, SheetContentsHandler sheetHandler, InputStream sheetInputStream) throws IOException, ParserConfigurationException, SAXException {
        DataFormatter formatter = new DataFormatter();
        InputSource sheetSource = new InputSource(sheetInputStream);
        try {
            XMLReader sheetParser = SAXHelper.newXMLReader();
            ContentHandler handler = new XSSFSheetXMLHandler(styles, null, strings, sheetHandler, formatter, false);
            sheetParser.setContentHandler(handler);
            sheetParser.parse(sheetSource);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
        }
    }

    /**
     * Initiates the processing of the XLS workbook file to CSV.
     *
     * @throws IOException
     * @throws OpenXML4JException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public void process() throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
        XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
        StylesTable styles = xssfReader.getStylesTable();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
        while (iter.hasNext()) {
            InputStream stream = iter.next();
            sheetName = iter.getSheetName();
            processSheet(styles, strings, new SheetToCSV(), stream);
            stream.close();
            ++sheetIndex;
        }
    }
}
