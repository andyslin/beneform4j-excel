package com.forms.beneform4j.excel.core.imports.parse;

import org.junit.Test;

import com.forms.beneform4j.excel.core.imports.stream.WorkbookStreamUtils;
import com.forms.beneform4j.excel.core.imports.stream.impl.TextWorkbookStreamHandler;

public class WorkbookParseUtilsTest {

    private TextWorkbookStreamHandler csvHandler = new TextWorkbookStreamHandler();

    @Test
    public void toCsvXlsx() {
        String excel = "D:/excel-test/multi/paramDef(TREE-A-TEST).xlsx";
        String csv = "D:/excel-test/multi/paramDef(TREE-A-TEST).xlsx.csv";
        csvHandler.setFilename(csv);
        WorkbookStreamUtils.parse(excel, csvHandler);
    }

    //@Test
    public void toCsvXls() {
        String excel = "D:/excel-test/multi/paramDef(TREE-N-TEST).xls";
        String csv = "D:/excel-test/multi/paramDef(TREE-N-TEST).xls.csv";
        csvHandler.setFilename(csv);
        csvHandler.setSkipRows(3);
        csvHandler.setIgnoreEmptyRow(true);
        //csvHandler.setAddBatchNo(true);
        csvHandler.setAddDataIndex(true);
        WorkbookStreamUtils.parse(excel, csvHandler);
    }
}
