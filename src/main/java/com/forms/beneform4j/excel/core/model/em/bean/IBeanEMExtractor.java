package com.forms.beneform4j.excel.core.model.em.bean;

import java.io.Serializable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface IBeanEMExtractor extends Serializable {

    /**
     * 提取数据
     * 
     * @param property
     * @param workbook
     * @param sheet
     * @param row
     * @param cell
     * @param type
     * @return
     */
    public BeanEMExtractResult extract(IBeanEMProperty property, Workbook workbook, Sheet sheet, Row row, Cell cell, Class<?> type);
}
