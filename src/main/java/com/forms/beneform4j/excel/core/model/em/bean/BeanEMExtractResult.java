package com.forms.beneform4j.excel.core.model.em.bean;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Bean模型的提取器提取结果<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class BeanEMExtractResult {

    /**
     * 内部枚举类，表示解析当前结果后的下一处理类型
     */
    public enum NextStep {
        /**
         * 正常情况下，继续处理下一属性
         */
        NORMAL,
        /**
         * 继续处理下一单元格
         */
        CONTINUE_CELL,
        /**
         * 继续处理下一行
         */
        CONTINUE_ROW,
        /**
         * 继续处理下一表单
         */
        CONTINUE_SHEET,
        /**
         * 结束处理
         */
        END;
    }

    private NextStep nextStep = NextStep.NORMAL;

    /**
     * 解析结果值
     */
    private Object value;

    /**
     * 下一处理需要跳过的表单数
     */
    private int offsetSheet;

    /**
     * 下一处理需要跳过的列数
     */
    private int offsetX;

    /**
     * 下一处理需要跳过的行数
     */
    private int offsetY;

    public NextStep getNextStep() {
        return nextStep;
    }

    public void setNextStep(NextStep nextStep) {
        this.nextStep = nextStep;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
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
}
