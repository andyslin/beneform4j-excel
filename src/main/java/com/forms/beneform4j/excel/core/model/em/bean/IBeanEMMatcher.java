package com.forms.beneform4j.excel.core.model.em.bean;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface IBeanEMMatcher {

    /**
     * 是否匹配
     * 
     * @param workbook
     * @param sheet
     * @param row
     * @param cell
     * @return
     */
    public boolean isMatch(Workbook workbook, Sheet sheet, Row row, Cell cell);
}
