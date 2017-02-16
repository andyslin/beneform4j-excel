package com.forms.beneform4j.excel.core.model.em.bean.impl.extractor;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.forms.beneform4j.excel.core.ExcelUtils;
import com.forms.beneform4j.excel.core.model.em.bean.BeanEMExtractResult;
import com.forms.beneform4j.excel.core.model.em.bean.BeanEMExtractResult.NextStep;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMProperty;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 基本的提取器实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class BaseBeanEMExtractor extends AbstractBeanEMExtractor {

    /**
     * 
     */
    private static final long serialVersionUID = 4281797394534292161L;

    private NextStep nextStep;

    private int offsetSheet;

    private int offsetX;

    private int offsetY;

    private int skipOffsetSheet;

    private int skipOffsetX;

    private int skipOffsetY;

    @Override
    public BeanEMExtractResult extract(IBeanEMProperty property, Cell cell, Class<?> type) {
        BeanEMExtractResult result = newExtractResult();
        result.setNextStep(getNextStep());
        result.setOffsetSheet(getSkipOffsetSheet());
        result.setOffsetX(getSkipOffsetX());
        result.setOffsetY(getSkipOffsetY());

        if (null != cell) {
            Sheet sheet = cell.getSheet();
            Workbook workbook = sheet.getWorkbook();
            if (0 != getOffsetSheet()) {
                sheet = workbook.getSheetAt(workbook.getSheetIndex(sheet) + getOffsetSheet());
            }
            if (null != sheet) {
                Cell mCell = ExcelUtils.getOffsetCell(cell, getOffsetX(), getOffsetY());
                if (null != mCell) {
                    setExtractResultValue(result, mCell, type);
                }
            }
        }
        return result;
    }

    public NextStep getNextStep() {
        return nextStep;
    }

    public void setNextStep(NextStep nextStep) {
        this.nextStep = nextStep;
    }

    public int getOffsetSheet() {
        return offsetSheet;
    }

    public void setOffsetSheet(int offsetSheet) {
        this.offsetSheet = offsetSheet;
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

    public int getSkipOffsetSheet() {
        return skipOffsetSheet;
    }

    public void setSkipOffsetSheet(int skipOffsetSheet) {
        this.skipOffsetSheet = skipOffsetSheet;
    }

    public int getSkipOffsetX() {
        return skipOffsetX;
    }

    public void setSkipOffsetX(int skipOffsetX) {
        this.skipOffsetX = skipOffsetX;
    }

    public int getSkipOffsetY() {
        return skipOffsetY;
    }

    public void setSkipOffsetY(int skipOffsetY) {
        this.skipOffsetY = skipOffsetY;
    }
}
