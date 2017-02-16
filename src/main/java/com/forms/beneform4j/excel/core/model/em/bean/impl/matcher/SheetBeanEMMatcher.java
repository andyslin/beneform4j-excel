package com.forms.beneform4j.excel.core.model.em.bean.impl.matcher;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMMatcher;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Sheet表单的匹配器实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class SheetBeanEMMatcher implements IBeanEMMatcher {

    private int sheetIndex = -1;

    private String sheetName = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMatch(Cell cell) {
        if (!isMatcher(cell)) {
            return false;
        }
        return doMatch(cell);
    }

    public boolean doMatch(Cell cell) {
        return false;
    }

    /**
     * 表单是否匹配
     * 
     * @param cell
     * @return
     */
    protected boolean isMatcher(Cell cell) {
        if (null == cell) {
            return false;
        }
        Sheet sheet = cell.getSheet();
        Workbook workbook = sheet.getWorkbook();
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
