package com.forms.beneform4j.excel.core.imports.parse;

import java.util.List;

import com.forms.beneform4j.excel.core.imports.parse.impl.WorkbookParseHandlerSupport;

public class ConsoleWorkbookParseHandler extends WorkbookParseHandlerSupport {

    @Override
    public void initialize() {
        System.out.println("initialize:");
        super.initialize();
    }

    @Override
    public boolean startSheet(int sheetIndex, String sheetName) {
        System.out.println("sheetIndex:" + sheetIndex);
        System.out.println("sheetName:" + sheetName);
        return super.startSheet(sheetIndex, sheetName);
    }

    @Override
    public boolean endSheet(int sheetIndex, String sheetName) {
        System.out.println("sheetIndex:" + sheetIndex);
        System.out.println("sheetName:" + sheetName);
        return super.endSheet(sheetIndex, sheetName);
    }

    @Override
    public void end() {
        System.out.println("end:");
        super.end();
    }

    @Override
    protected boolean handleRow(HandlerStatus status, List<String> cells, int rowIndex) {
        if (rowIndex > 20) {
            return false;
        }
        System.out.print(rowIndex + ":");
        for (String cell : cells) {
            System.out.print(cell + "!|!");
        }
        System.out.println();
        return super.handleRow(status, cells, rowIndex);
    }
}
