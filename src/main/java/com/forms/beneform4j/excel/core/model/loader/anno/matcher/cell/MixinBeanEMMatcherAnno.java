package com.forms.beneform4j.excel.core.model.loader.anno.matcher.cell;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMMatcher;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MixinBeanEMMatcherAnno {

    /**
     * 使用Spring时的bean名称
     * 
     * @return
     */
    String beanName() default "";

    /**
     * 实际匹配器的实现类型
     * 
     * @return
     */
    Class<? extends IBeanEMMatcher> beanType() default IBeanEMMatcher.class;
}
