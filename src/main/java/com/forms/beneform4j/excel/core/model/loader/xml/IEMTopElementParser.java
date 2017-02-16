package com.forms.beneform4j.excel.core.model.loader.xml;

import org.w3c.dom.Element;

import com.forms.beneform4j.excel.core.model.loader.IResourceEMLoadContext;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : XML模型中一级元素的解析器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public interface IEMTopElementParser {

    /**
     * 解析一级元素
     * 
     * @param context 加载上下文
     * @param element 一级元素
     */
    public void parse(IResourceEMLoadContext context, Element element);
}
