package com.forms.beneform4j.excel.core.model.loader.anno.extractor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMExtractor;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Bean模型中混入其它实现的注解<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
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
