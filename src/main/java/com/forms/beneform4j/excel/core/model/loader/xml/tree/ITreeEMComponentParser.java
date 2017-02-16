package com.forms.beneform4j.excel.core.model.loader.xml.tree;

import org.w3c.dom.Element;

import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 树型XML配置中的组件子元素的解析器接口<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public interface ITreeEMComponentParser {

    /**
     * 解析组件子元素
     * 
     * @param modelId 模型ID
     * @param container 包含组件的容器元素
     * @return
     */
    public ITreeEMComponent parse(String modelId, Element container);
}
