package com.forms.beneform4j.excel.core.model.loader.xml.tree;

import org.w3c.dom.Element;

import com.forms.beneform4j.excel.core.model.em.tree.ITreeEM;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : TreeEM元素装饰器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public interface ITreeEMDecorator {

    /**
     * 装饰
     * 
     * @param model
     * @param workbook
     * @param decorator
     */
    public void decorate(ITreeEM model, Element workbook, Element decorator);
}
