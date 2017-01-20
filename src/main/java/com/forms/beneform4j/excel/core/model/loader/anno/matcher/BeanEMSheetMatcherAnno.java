package com.forms.beneform4j.excel.core.model.loader.anno.matcher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanEMSheetMatcherAnno {

    int value() default -1;

    String name() default "";
}
