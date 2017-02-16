package com.forms.beneform4j.excel.core.model.loader.anno.matcher.end;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.forms.beneform4j.excel.core.model.loader.anno.matcher.cell.BaseBeanEMMatcherAnno;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Bean模型中使用基本匹配器表示嵌套对象或列表对象结束的注解<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseBeanEMEndMatcherAnno {

    BaseBeanEMMatcherAnno value();
}
