package com.forms.beneform4j.excel.core.model.em.text.impl;

import com.forms.beneform4j.excel.core.model.em.base.BaseEM;
import com.forms.beneform4j.excel.core.model.em.text.ITextEM;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 转换为文本文件的Excel配置模型实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class TextEM extends BaseEM implements ITextEM {

    /**
     * 
     */
    private static final long serialVersionUID = -7256004639693338794L;

    /**
     * 字符集
     */
    private String charset;

    /**
     * 开始跳过的行数
     */
    private int skipRows = -1;

    /**
     * 获取每一行最少的列数，如果不足此数，使用默认单元格的值填充
     */
    private int minCellsOfRow = -1;

    /**
     * 获取默认单元格的值
     */
    private String defaultCellValue = "";

    /**
     * 一行数据的分隔符，默认为英文逗号,
     */
    private String separator = ",";

    /**
     * 是否忽略空行，默认为忽略
     */
    private boolean ignoreEmptyRow = true;

    /**
     * 批次号的格式 [d{yyyyMMdd}(a|n|an){8}]
     */
    private String batchNoFormat = "d{yyyyMMddhhmmss}n{18}";

    /**
     * 是否添加批次号
     */
    private boolean addBatchNo = true;

    /**
     * 是否添加行索引，每个Sheet从1开始
     */
    private boolean addRowIndex = false;

    /**
     * 是否添加数据索引，索引从1开始，所有Sheet累加
     */
    private boolean addDataIndex = true;

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public int getSkipRows() {
        return skipRows;
    }

    public void setSkipRows(int skipRows) {
        this.skipRows = skipRows;
    }

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

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public boolean isIgnoreEmptyRow() {
        return ignoreEmptyRow;
    }

    public void setIgnoreEmptyRow(boolean ignoreEmptyRow) {
        this.ignoreEmptyRow = ignoreEmptyRow;
    }

    public String getBatchNoFormat() {
        return batchNoFormat;
    }

    public void setBatchNoFormat(String batchNoFormat) {
        this.batchNoFormat = batchNoFormat;
    }

    public boolean isAddBatchNo() {
        return addBatchNo;
    }

    public void setAddBatchNo(boolean addBatchNo) {
        this.addBatchNo = addBatchNo;
    }

    public boolean isAddRowIndex() {
        return addRowIndex;
    }

    public void setAddRowIndex(boolean addRowIndex) {
        this.addRowIndex = addRowIndex;
    }

    public boolean isAddDataIndex() {
        return addDataIndex;
    }

    public void setAddDataIndex(boolean addDataIndex) {
        this.addDataIndex = addDataIndex;
    }

}
