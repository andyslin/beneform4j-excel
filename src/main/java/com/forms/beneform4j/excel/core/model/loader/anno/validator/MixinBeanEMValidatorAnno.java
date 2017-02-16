package com.forms.beneform4j.excel.core.model.loader.anno.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMValidator;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Bean模型中混入其它实现的校验器的注解<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
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
