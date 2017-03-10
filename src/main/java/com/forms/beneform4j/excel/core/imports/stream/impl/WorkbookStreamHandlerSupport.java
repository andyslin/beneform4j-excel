package com.forms.beneform4j.excel.core.imports.stream.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.imports.stream.IWorkbookStreamHandler;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 逐行处理的回调处理器支持类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class WorkbookStreamHandlerSupport implements IWorkbookStreamHandler {

    private static final BatchNoFormat DEFAULT_BATCH_NO_FORMAT = new BatchNoFormat("yyyyMMddhhmmss", "n", 18);

    private static final Pattern BATCH_NO_FORMAT_PATTERN = Pattern.compile("^\\s*d\\s*\\{\\s*(\\w+)\\s*\\}\\s*(a|n|an)?\\s*\\{\\s*(\\d+)\\s*\\}\\s*$");

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

    /**
     * 批次号格式
     */
    private String batchNoFormat;

    @Override
    public void initialize() {
        if (null == defaultCellValue) {
            defaultCellValue = "";
        }
        BatchNoFormat format = initializeBatchNoFormat(this.getBatchNoFormat());
        status.batchNo = generateBatchNo(format);
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
        if (this.preHandleRow(cells, rowIndex)) {
            int insertColumns = insertColumns(cells);
            return handleRow(status, cells, insertColumns);
        }
        return true;
    }

    /**
     * 处理一行数据前的预处理
     * 
     * @param cells
     * @param rowIndex
     * @return 是否需要继续处理该行数据
     */
    protected boolean preHandleRow(List<String> cells, int rowIndex) {
        if (rowIndex < getSkipRows()) {// 跳过前面多少行
            return false;
        }
        boolean isEmptyRow = cells.isEmpty();
        if (isIgnoreEmptyRow() && isEmptyRow) {// 忽略空行
            return false;
        } else {
            status.rowIndex = rowIndex + 1;
            status.dataIndex++;
        }

        int minCellsOfRow = getMinCellsOfRow();
        if (minCellsOfRow > 0) {//最少列
            for (int i = minCellsOfRow - cells.size(); i > 0; i--) {
                cells.add(getDefaultCellValue());
            }
        }
        return true;
    }

    /**
     * 添加额外的列数据
     * 
     * @param cells
     * @return 添加的列数
     */
    protected int insertColumns(List<String> cells) {
        int insertColumns = 0;
        if (this.isAddRowIndex()) {
            cells.add(0, status.rowIndex + "");
            insertColumns++;
        }
        if (this.isAddDataIndex()) {
            cells.add(0, status.dataIndex + "");
            insertColumns++;
        }
        if (this.isAddBatchNo()) {
            cells.add(0, status.batchNo);
            insertColumns++;
        }
        return insertColumns;
    }

    /**
     * 处理一行数据，返回是否结束整个解析
     * 
     * @param status 当前解析状态
     * @param cells 行数据
     * @param insertColumns 在原数据前已插入了多少列数据
     * @return 是否结束整个解析
     */
    protected boolean handleRow(HandlerStatus status, List<String> cells, int insertColumns) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean endSheet(int sheetIndex, String sheetName) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
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
     * 获取批次号格式
     * 
     * @return
     */
    public String getBatchNoFormat() {
        return batchNoFormat;
    }

    /**
     * 设置批次号格式
     * 
     * @param batchNoFormat
     */
    public void setBatchNoFormat(String batchNoFormat) {
        this.batchNoFormat = batchNoFormat;
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
    protected String generateBatchNo(BatchNoFormat format) {
        StringBuffer sb = new StringBuffer();
        sb.append(format.getDateFormat().format(new Date()));
        String type = format.getType();
        int length = format.getRandomLength();
        if ("a".equalsIgnoreCase(type)) {//字母
            sb.append(RandomStringUtils.randomAlphabetic(length));
        } else if ("n".equalsIgnoreCase(type)) {//数字
            sb.append(RandomStringUtils.randomNumeric(length));
        } else if ("an".equalsIgnoreCase(type)) {//字母数字
            sb.append(RandomStringUtils.randomAlphanumeric(length));
        } else {
            sb.append(RandomStringUtils.random(length));
        }
        return sb.toString();
    }

    private BatchNoFormat initializeBatchNoFormat(String format) {
        if (!CoreUtils.isBlank(format)) {
            Matcher matcher = BATCH_NO_FORMAT_PATTERN.matcher(format);
            if (matcher.find()) {
                String dateFormat = matcher.group(1);
                String type = matcher.group(2);
                int randomLength = Integer.parseInt(matcher.group(3));
                return new BatchNoFormat(dateFormat, type, randomLength);
            } else {
                Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS31, format);
            }
        }
        return DEFAULT_BATCH_NO_FORMAT;
    }

    /**
     * 处理器状态
     */
    public class HandlerStatus {

        /**
         * 批次号
         */
        private String batchNo;

        /**
         * 当前正在处理的sheet索引，从0开始
         */
        private int sheetIndex;

        /**
         * 当前正在处理的sheet名称
         */
        private String sheetName;

        /**
         * 当前正在处理的行索引，每一个sheet都从1开始
         */
        private int rowIndex;

        /**
         * 当前正在处理的数据索引，从1开始，所有sheet累加计算，不包括被忽略的行
         */
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

    /**
     * 批次号格式
     */
    protected static class BatchNoFormat {
        private final SimpleDateFormat dateFormat;
        private final String type;
        private final int randomLength;

        private BatchNoFormat(String dateFormat, String type, int randomLength) {
            this.dateFormat = new SimpleDateFormat(dateFormat);
            this.type = type;
            this.randomLength = randomLength;
        }

        public SimpleDateFormat getDateFormat() {
            return dateFormat;
        }

        public String getType() {
            return type;
        }

        public int getRandomLength() {
            return randomLength;
        }
    }
}
