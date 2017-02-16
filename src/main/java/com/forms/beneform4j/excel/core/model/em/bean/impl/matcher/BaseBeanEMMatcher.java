package com.forms.beneform4j.excel.core.model.em.bean.impl.matcher;

import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.core.ExcelUtils;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 基本的匹配器实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class BaseBeanEMMatcher extends SheetBeanEMMatcher {

    /**
     * 在水平方向上的偏移
     * 
     * @return
     */
    private int offsetX;

    /**
     * 在垂直方向上的偏移
     * 
     * @return
     */
    private int offsetY;

    /**
     * 是否为合并的单元格
     * 
     * @return
     */
    private boolean isMergeCell;

    /**
     * 是否忽略大小写
     */
    private boolean ignoreCase;

    /**
     * 值等于
     * 
     * @return
     */
    private String value;

    /**
     * 值满足正则表达式
     * 
     * @return
     */
    private String pattern;

    @Override
    public boolean doMatch(Cell cell) {
        Cell mCell = null;
        if (isMergeCell()) {
            mCell = ExcelUtils.getMergetCell(cell.getSheet(), cell.getRowIndex() - getOffsetX(), cell.getColumnIndex() - getOffsetY());
        } else {
            mCell = ExcelUtils.getOffsetCell(cell, -getOffsetX(), -getOffsetY());
        }
        if (null == mCell) {
            return false;
        }
        String value = ExcelUtils.getCellValue(mCell);
        if (CoreUtils.isBlank(value)) {
            return false;
        } else {
            value = value.trim();
            boolean ignoreCase = isIgnoreCase();
            if (!CoreUtils.isBlank(getValue())) {
                if (ignoreCase) {
                    return value.equalsIgnoreCase(getValue());
                } else {
                    return value.equals(getValue());
                }
            } else if (!CoreUtils.isBlank(getPattern())) {
                if (ignoreCase) {
                    return Pattern.compile(getPattern(), Pattern.CASE_INSENSITIVE).matcher(value).find();
                } else {
                    return Pattern.compile(getPattern()).matcher(value).find();
                }
            }
        }
        return false;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public boolean isMergeCell() {
        return isMergeCell;
    }

    public void setMergeCell(boolean isMergeCell) {
        this.isMergeCell = isMergeCell;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
