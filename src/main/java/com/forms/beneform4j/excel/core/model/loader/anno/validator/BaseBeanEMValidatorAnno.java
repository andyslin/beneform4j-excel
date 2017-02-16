package com.forms.beneform4j.excel.core.model.loader.anno.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Bean模型中基本校验器的注解<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseBeanEMValidatorAnno {

    /**
     * 在水平方向上的偏移
     * 
     * @return
     */
    int offsetX() default 0;

    /**
     * 在垂直方向上的偏移
     * 
     * @return
     */
    int offsetY() default 0;

    /**
     * 是否忽略大小写
     * 
     * @return
     */
    boolean ignoreCase() default false;

    /**
     * 是否允许为空
     * 
     * @return
     */
    boolean allowEmpty() default false;

    /**
     * 校验通过的情况下是否继续解析
     * 
     * @return
     */
    boolean isContinue() default false;

    /**
     * 值等于
     * 
     * @return
     */
    String value() default "";

    /**
     * 值满足正则表达式
     * 
     * @return
     */
    String pattern() default "";
}
