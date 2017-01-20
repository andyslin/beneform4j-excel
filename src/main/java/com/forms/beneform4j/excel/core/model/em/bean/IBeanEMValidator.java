package com.forms.beneform4j.excel.core.model.em.bean;

import java.io.Serializable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface IBeanEMValidator extends Serializable {

    /**
     * 校验，通过返回null，不通过时抛出异常
     * 
     * @param workbook
     * @param sheet
     * @param row
     * @param cell
     * @param type
     */
    public void validate(Workbook workbook, Sheet sheet, Row row, Cell cell, Class<?> type);
}
