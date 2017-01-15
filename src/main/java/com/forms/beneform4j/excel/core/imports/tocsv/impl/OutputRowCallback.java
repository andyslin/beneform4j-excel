package com.forms.beneform4j.excel.core.imports.tocsv.impl;

import java.util.List;

import com.forms.beneform4j.excel.core.imports.tocsv.IRowCallback;

public class OutputRowCallback implements IRowCallback {

    @Override
    public boolean callback(List<String> cells, String sheetName, int sheetIndex, int index) {
        System.out.print(index + ":");
        if (null != cells) {
            for (String cell : cells) {
                System.out.println(cell + "!|!");
            }
        }
        System.out.println();
        return true;
    }

}
