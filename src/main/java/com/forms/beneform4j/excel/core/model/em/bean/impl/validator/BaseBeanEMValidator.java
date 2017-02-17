package com.forms.beneform4j.excel.core.model.em.bean.impl.validator;

import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.ExcelUtils;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMValidator;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 基本的Bean模型的校验器实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class BaseBeanEMValidator implements IBeanEMValidator {

    /**
     * 
     */
    private static final long serialVersionUID = 59306217585805994L;

    /**
     * 是否允许为空
     */
    private boolean allowEmpty;

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
    public void validate(Cell cell, Class<?> fieldType) {
        String sheetName = cell.getSheet().getSheetName();
        Cell mCell = ExcelUtils.getOffsetCell(cell, getOffsetX(), getOffsetY());
        if (null == mCell && !isAllowEmpty()) {
            Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS11, sheetName, cell.getRowIndex() + 1, cell.getColumnIndex() + 1, getOffsetX(), getOffsetY());
        }

        int rowIndex = mCell.getRowIndex() + 1;
        int cellIndex = mCell.getColumnIndex() + 1;
        String val = ExcelUtils.getCellValue(mCell);
        if (CoreUtils.isBlank(val) && !isAllowEmpty()) {
            Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS12, sheetName, rowIndex, cellIndex);
        } else {
            val = val.trim();
        }

        String value = getValue();
        if (!CoreUtils.isBlank(value)) {
            value = value.trim();
            if (isIgnoreCase()) {
                if (!value.equalsIgnoreCase(val)) {
                    Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS13, sheetName, rowIndex, cellIndex, value);
                }
            } else {
                if (!value.equals(val)) {
                    Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS14, sheetName, rowIndex, cellIndex, value);
                }
            }
        }

        String pattern = getPattern();
        if (!CoreUtils.isBlank(pattern)) {
            pattern = pattern.trim();
            if (isIgnoreCase()) {
                if (Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(val).find()) {
                    Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS15, sheetName, rowIndex, cellIndex, pattern);
                }
            } else {
                if (Pattern.compile(pattern).matcher(val).find()) {
                    Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS16, sheetName, rowIndex, cellIndex, pattern);
                }
            }
        }
    }

    public boolean isAllowEmpty() {
        return allowEmpty;
    }

    public void setAllowEmpty(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
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
