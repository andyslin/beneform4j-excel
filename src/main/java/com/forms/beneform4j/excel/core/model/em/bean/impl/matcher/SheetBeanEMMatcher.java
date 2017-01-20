package com.forms.beneform4j.excel.core.model.em.bean.impl.matcher;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMMatcher;

public class SheetBeanEMMatcher implements IBeanEMMatcher {

    private int sheetIndex = -1;

    private String sheetName = null;

    @Override
    public boolean isMatch(Workbook workbook, Sheet sheet, Row row, Cell cell) {
        if (!isMatcher(workbook, sheet)) {
            return false;
        }
        return doMatch(workbook, sheet, row, cell);
    }

    public boolean doMatch(Workbook workbook, Sheet sheet, Row row, Cell cell) {
        return false;
    }

    /**
     * 表单是否匹配
     * 
     * @param workbook
     * @param sheet
     * @return
     */
    protected boolean isMatcher(Workbook workbook, Sheet sheet) {
        int index = getSheetIndex();
        if (-1 != index) {
            return workbook.getSheetAt(index) == sheet;
        } else {
            String name = getSheetName();
            if (null != name) {
                return name.equals(sheet.getSheetName());
            } else {
                return workbook.getSheetAt(0) == sheet;
            }
        }
    }

    //    public boolean isChange() {
    //        return !(null == sheetName && -1 == sheetIndex);
    //    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}
