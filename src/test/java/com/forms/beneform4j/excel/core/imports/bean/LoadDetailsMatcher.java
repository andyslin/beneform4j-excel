package com.forms.beneform4j.excel.core.imports.bean;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.forms.beneform4j.excel.core.ExcelUtils;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMMatcher;

public class LoadDetailsMatcher implements IBeanEMMatcher {

    /**
     * 定位内层循环的开始
     */
    @Override
    public boolean isMatch(Workbook workbook, Sheet sheet, Row row, Cell cell) {
        String value = ExcelUtils.getCellValue(cell);
        //          return null != value && value.startsWith("测试产品");

        //          System.out.println("rowIndex:"+cell.getRowIndex()+"；cellIndex:"+cell.getColumnIndex()+"；value:"+ExcelUtils.getCellValue(cell));
        if ("产品简称".equals(value)) {
            return false;
        }
        Cell vCell = ExcelUtils.getMergetCell(sheet, cell.getRowIndex(), 0);
        if (null == vCell) {
            return false;
        }
        value = ExcelUtils.getCellValue(vCell);
        if ("工作量说明".equals(value)) {
            vCell = cell.getRow().getCell(cell.getColumnIndex() - 1);
            value = ExcelUtils.getCellValue(vCell);
            if ("工作量说明".equals(value) || "".equals(value)) {
                return true;
            }
        }
        return false;
    }

}
