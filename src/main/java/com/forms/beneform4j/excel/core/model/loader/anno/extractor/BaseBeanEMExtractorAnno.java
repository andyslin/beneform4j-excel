package com.forms.beneform4j.excel.core.model.loader.anno.extractor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.forms.beneform4j.excel.core.model.em.bean.BeanEMExtractResult.NextStep;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Bean模型中基本的提取器注解<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseBeanEMExtractorAnno {

    /**
     * 循环处理情况
     * 
     * @return
     */
    NextStep nextStep() default NextStep.NORMAL;

    /**
     * 解析时表单的偏移
     * 
     * @return
     */
    int offsetSheet() default 0;

    /**
     * 解析时在水平方向上的偏移
     * 
     * @return
     */
    int offsetX() default 0;

    /**
     * 解析时在垂直方向上的偏移
     * 
     * @return
     */
    int offsetY() default 0;

    /**
     * 解析后循环处理时表单的偏移
     * 
     * @return
     */
    int skipOffsetSheet() default 0;

    /**
     * 解析后循环处理时在水平方向上的偏移
     * 
     * @return
     */
    int skipOffsetX() default 0;

    /**
     * 解析后循环处理时在垂直方向上的偏移
     * 
     * @return
     */
    int skipOffsetY() default 0;
}
