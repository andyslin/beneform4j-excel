package com.forms.beneform4j.excel.core.imports.parse;

import org.junit.Test;

import com.forms.beneform4j.excel.core.imports.parse.impl.ToCsvWorkbookParseHandler;

public class WorkbookParseUtilsTest {

    private ConsoleWorkbookParseHandler console = new ConsoleWorkbookParseHandler();

    private ToCsvWorkbookParseHandler csvHandler = new ToCsvWorkbookParseHandler();

    @Test
    public void toCsvXlsx() {
        String excel = "D:/excel-test/multi/paramDef(TREE-A-TEST).xlsx";
        String csv = "D:/excel-test/multi/paramDef(TREE-A-TEST).xlsx.csv";
        csvHandler.setFilename(csv);
        csvHandler.setIgnoreEmptyRow(true);
        WorkbookParseUtils.parse(excel, csvHandler);
    }

    @Test
    public void toCsvXls() {
        String excel = "D:/excel-test/multi/paramDef(TREE-A-TEST).xls";
        String csv = "D:/excel-test/multi/paramDef(TREE-A-TEST).xls.csv";
        csvHandler.setFilename(csv);
        WorkbookParseUtils.parse(excel, csvHandler);
    }

    @Test
    public void toConsoleXlsx() {
        System.out.println("xlsx");
        String excel = "D:/excel-test/multi/paramDef(TREE-A-TEST).xlsx";
        WorkbookParseUtils.parse(excel, console);
    }

    @Test
    public void toConsoleXls() {
        System.out.println("xls");
        String excel = "D:/excel-test/multi/paramDef(TREE-A-TEST).xls";
        console.setMinCellsOfRow(10);
        WorkbookParseUtils.parse(excel, console);
    }
}
