package com.forms.beneform4j.excel.core.imports.stream.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import com.forms.beneform4j.excel.core.imports.stream.IWorkbookStreamHandler;

public class WorkbookStreamHandlerSupport implements IWorkbookStreamHandler {

    /**
     * 用于生成批次号的日期格式
     */
    private final SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 处理器状态
     */
    private final HandlerStatus status = new HandlerStatus();

    /**
     * 每个Sheet跳过的行数（包括空行），默认不跳过
     */
    private int skipRows;

    /**
     * 每行最少多少列，如果不足，则补上默认值，列数不包括可能添加的数据索引
     */
    private int minCellsOfRow;

    /**
     * 单元格的默认值，不足最少列时，补充的字符串，默认为""
     */
    private String defaultCellValue;

    /**
     * 是否忽略空行，默认忽略
     */
    private boolean ignoreEmptyRow = true;

    /**
     * 是否添加批次号
     */
    private boolean addBatchNo;

    /**
     * 是否添加Excel中的行索引，索引从1开始
     */
    private boolean addRowIndex;

    /**
     * 是否添加数据索引，索引从1开始，如果有表头或空行，数据索引一般不等于行索引
     */
    private boolean addDataIndex;

    @Override
    public void initialize() {
        if (null == defaultCellValue) {
            defaultCellValue = "";
        }
        status.batchNo = generateBatchNo();
        status.dataIndex = 0;
    }

    @Override
    public boolean startSheet(int sheetIndex, String sheetName) {
        status.sheetIndex = sheetIndex;
        status.sheetName = sheetName;
        return true;
    }

    @Override
    public boolean row(List<String> cells, int rowIndex) {
        if (rowIndex < getSkipRows()) {// 跳过前面多少行
            return true;
        }
        boolean isEmptyRow = cells.isEmpty();
        if (isIgnoreEmptyRow() && isEmptyRow) {// 忽略空行
            return true;
        } else {
            status.rowIndex = rowIndex + 1;
            status.dataIndex++;
        }

        if (minCellsOfRow > 0) {//最少列
            for (int i = minCellsOfRow - cells.size(); i > 0; i--) {
                cells.add(getDefaultCellValue());
            }
        }

        if (this.isAddRowIndex()) {
            cells.add(0, (status.rowIndex) + "");
        }
        if (this.isAddDataIndex()) {
            cells.add(0, (status.dataIndex) + "");
        }
        if (this.isAddBatchNo()) {
            cells.add(0, status.batchNo);
        }
        return handleRow(status, cells, rowIndex, isEmptyRow);
    }

    protected boolean handleRow(HandlerStatus status, List<String> cells, int rowIndex, boolean isEmptyRow) {
        return true;
    }

    @Override
    public boolean endSheet(int sheetIndex, String sheetName) {
        return true;
    }

    @Override
    public void end() {}

    /**
     * 获取每个Sheet跳过的前多少行
     * 
     * @return
     */
    public int getSkipRows() {
        return skipRows;
    }

    /**
     * 设置每个Sheet跳过的前多少行，包括空行
     * 
     * @param skipRows
     */
    public void setSkipRows(int skipRows) {
        this.skipRows = skipRows;
    }

    /**
     * 获取每行最少多少列
     * 
     * @return
     */
    public int getMinCellsOfRow() {
        return minCellsOfRow;
    }

    /**
     * 设置每行最少多少列
     * 
     * @param minCellsOfRow
     */
    public void setMinCellsOfRow(int minCellsOfRow) {
        this.minCellsOfRow = minCellsOfRow;
    }

    /**
     * 获取默认单元格的值
     */
    @Override
    public String getDefaultCellValue() {
        return defaultCellValue;
    }

    /**
     * 设置默认单元格的值
     * 
     * @param defaultCellValue
     */
    public void setDefaultCellValue(String defaultCellValue) {
        this.defaultCellValue = defaultCellValue;
    }

    /**
     * 是否忽略空行
     * 
     * @return
     */
    public boolean isIgnoreEmptyRow() {
        return ignoreEmptyRow;
    }

    /**
     * 设置是否忽略空行
     * 
     * @param ignoreEmptyRow
     */
    public void setIgnoreEmptyRow(boolean ignoreEmptyRow) {
        this.ignoreEmptyRow = ignoreEmptyRow;
    }

    /**
     * 是否添加批次号
     * 
     * @return
     */
    public boolean isAddBatchNo() {
        return addBatchNo;
    }

    /**
     * 设置是否添加批次号
     * 
     * @param addBatchNo
     */
    public void setAddBatchNo(boolean addBatchNo) {
        this.addBatchNo = addBatchNo;
    }

    /**
     * 是否添加原Excel中的行索引，每个Sheet从1开始
     * 
     * @return
     */
    public boolean isAddRowIndex() {
        return addRowIndex;
    }

    /**
     * 设置是否添加原Excel中的行索引，每个Sheet从1开始
     * 
     * @param addRowIndex
     */
    public void setAddRowIndex(boolean addRowIndex) {
        this.addRowIndex = addRowIndex;
    }

    /**
     * 是否添加数据索引，所有Sheet统一编号，并且不计算被跳过和忽略的行数，从1开始
     * 
     * @return
     */
    public boolean isAddDataIndex() {
        return addDataIndex;
    }

    /**
     * 设置是否添加数据索引，所有Sheet统一编号，并且不计算被跳过和忽略的行数，从1开始
     * 
     * @param addDataIndex
     */
    public void setAddDataIndex(boolean addDataIndex) {
        this.addDataIndex = addDataIndex;
    }

    /**
     * 获取处理状态
     * 
     * @return
     */
    public HandlerStatus getStatus() {
        return status;
    }

    /**
     * 生成批次号
     */
    protected String generateBatchNo() {
        StringBuffer sb = new StringBuffer();
        sb.append(df.format(new Date()));
        sb.append(RandomStringUtils.randomNumeric(18));
        return sb.toString();
    }

    public class HandlerStatus {

        private String batchNo;

        private int sheetIndex;

        private String sheetName;

        private int rowIndex;

        private int dataIndex;

        /**
         * 批次号
         * 
         * @return
         */
        public String getBatchNo() {
            return batchNo;
        }

        /**
         * Sheet索引，从0开始
         * 
         * @return
         */
        public int getSheetIndex() {
            return sheetIndex;
        }

        /**
         * Sheet名称
         * 
         * @return
         */
        public String getSheetName() {
            return sheetName;
        }

        /**
         * 行索引，每个Sheet从1开始
         * 
         * @return
         */
        public int getRowIndex() {
            return rowIndex;
        }

        /**
         * 数据索引，从1开始，所有Sheet累加
         * 
         * @return
         */
        public int getDataIndex() {
            return dataIndex;
        }
    }
}
