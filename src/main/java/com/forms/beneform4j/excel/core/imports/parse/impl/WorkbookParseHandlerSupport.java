package com.forms.beneform4j.excel.core.imports.parse.impl;

import java.util.List;

import com.forms.beneform4j.excel.core.imports.parse.IWorkbookParseHandler;

public class WorkbookParseHandlerSupport implements IWorkbookParseHandler {

    /**
     * 处理器状态
     */
    private final HandlerStatus status = new HandlerStatus();

    /**
     * 每行最少多少列，如果不足，则补上默认值
     */
    private int minCellsOfRow;

    /**
     * 单元格的默认值
     */
    private String defaultCellValue;

    /**
     * 是否忽略空行
     */
    private boolean ignoreEmptyRow;

    @Override
    public void initialize() {
        if (null == defaultCellValue) {
            defaultCellValue = "";
        }
    }

    @Override
    public boolean startSheet(int sheetIndex, String sheetName) {
        status.sheetIndex = sheetIndex;
        status.sheetName = sheetName;
        return true;
    }

    @Override
    public boolean row(List<String> cells, int rowIndex) {
        status.rowIndex = rowIndex;
        if (isIgnoreEmptyRow() && cells.isEmpty()) {
            return true;
        } else if (minCellsOfRow > 0) {
            for (int i = minCellsOfRow - cells.size(); i > 0; i--) {
                cells.add(getDefaultCellValue());
            }
        }
        return handleRow(status, cells, rowIndex);
    }

    protected boolean handleRow(HandlerStatus status, List<String> cells, int rowIndex) {
        return true;
    }

    @Override
    public boolean endSheet(int sheetIndex, String sheetName) {
        return true;
    }

    @Override
    public void end() {}

    public int getMinCellsOfRow() {
        return minCellsOfRow;
    }

    public void setMinCellsOfRow(int minCellsOfRow) {
        this.minCellsOfRow = minCellsOfRow;
    }

    public String getDefaultCellValue() {
        return defaultCellValue;
    }

    public void setDefaultCellValue(String defaultCellValue) {
        this.defaultCellValue = defaultCellValue;
    }

    public boolean isIgnoreEmptyRow() {
        return ignoreEmptyRow;
    }

    public void setIgnoreEmptyRow(boolean ignoreEmptyRow) {
        this.ignoreEmptyRow = ignoreEmptyRow;
    }

    public HandlerStatus getStatus() {
        return status;
    }

    public class HandlerStatus {

        private int sheetIndex;

        private String sheetName;

        private int rowIndex;

        public int getSheetIndex() {
            return sheetIndex;
        }

        public String getSheetName() {
            return sheetName;
        }

        public int getRowIndex() {
            return rowIndex;
        }
    }
}
