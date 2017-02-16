package com.forms.beneform4j.excel.core.model.loader.anno.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Bean模型中复合校验器的注解<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CompositeBeanEMValidatorAnno {

    /**
     * 使用简单校验器的校验器组
     * 
     * @return
     */
    BaseBeanEMValidatorAnno[] value() default {};

    /**
     * 使用混入校验器的校验器组
     * 
     * @return
     */
    MixinBeanEMValidatorAnno[] mixins() default {};

    /**
     * 是否为或的关系
     * 
     * @return
     */
    boolean isOr() default false;
}
