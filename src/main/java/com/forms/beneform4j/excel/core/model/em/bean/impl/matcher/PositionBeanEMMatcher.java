package com.forms.beneform4j.excel.core.model.em.bean.impl.matcher;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.forms.beneform4j.excel.core.ExcelUtils;

public class PositionBeanEMMatcher extends SheetBeanEMMatcher {

    /**
     * 行位置，对应于Excel中的行，从1开始，有效值1－65535，小于或等于0表示匹配所有行位置
     */
    private int rowPosition;

    /**
     * 列位置，对应于Excel中的列，例如：A、B、AA...，有效值A－IV，星号*表示匹配所有列位置
     */
    private String cellPosition;

    @Override
    public boolean doMatch(Workbook workbook, Sheet sheet, Row row, Cell cell) {
        int rowPosition = getRowPosition();
        String cellPosition = getCellPosition();
        if (rowPosition <= 0) {
            return ExcelUtils.getColumnPositionIndex(cellPosition) == cell.getColumnIndex();
        } else if ("*".equals(cellPosition)) {
            return ExcelUtils.getRowPositionIndex(rowPosition) == cell.getRowIndex();
        } else {
            return ExcelUtils.getRowPositionIndex(rowPosition) == cell.getRowIndex() && ExcelUtils.getColumnPositionIndex(cellPosition) == cell.getColumnIndex();
        }
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }

    public String getCellPosition() {
        return cellPosition;
    }

    public void setCellPosition(String cellPosition) {
        this.cellPosition = cellPosition;
    }
}
