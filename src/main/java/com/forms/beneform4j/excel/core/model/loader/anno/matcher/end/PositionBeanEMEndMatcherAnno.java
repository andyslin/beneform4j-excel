package com.forms.beneform4j.excel.core.model.loader.anno.matcher.end;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.forms.beneform4j.excel.core.model.loader.anno.matcher.cell.PositionBeanEMMatcherAnno;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PositionBeanEMEndMatcherAnno {

    PositionBeanEMMatcherAnno value();

}
