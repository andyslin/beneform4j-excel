package com.forms.beneform4j.excel.core.model.loader.anno.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMValidator;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MixinBeanEMValidatorAnno {

    /**
     * 使用Spring时的bean名称
     * 
     * @return
     */
    String beanName() default "";

    /**
     * 实际校验器的实现类型
     * 
     * @return
     */
    Class<? extends IBeanEMValidator> beanType() default IBeanEMValidator.class;
}
