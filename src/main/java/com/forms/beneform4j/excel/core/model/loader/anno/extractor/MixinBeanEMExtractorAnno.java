package com.forms.beneform4j.excel.core.model.loader.anno.extractor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMExtractor;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MixinBeanEMExtractorAnno {

    /**
     * Spring的BeanName或者类的全限定名
     * 
     * @return
     */
    String beanName() default "";

    /**
     * 提取器类型
     * 
     * @return
     */
    Class<? extends IBeanEMExtractor> beanType() default IBeanEMExtractor.class;
}
