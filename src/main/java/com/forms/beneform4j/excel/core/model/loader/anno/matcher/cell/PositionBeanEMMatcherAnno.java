package com.forms.beneform4j.excel.core.model.loader.anno.matcher.cell;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PositionBeanEMMatcherAnno {

    /**
     * 行位置，对应于Excel中的行，从1开始，有效值1－65535，小于0或等于表示匹配所有行位置
     * 
     * @return
     */
    int rowPosition() default -1;

    /**
     * 列位置，对应于Excel中的列，例如：A、B、AA...，有效值A－IV，星号*表示匹配所有列位置
     * 
     * @return
     */
    String cellPosition() default "*";

}
