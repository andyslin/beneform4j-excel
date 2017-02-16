package com.forms.beneform4j.excel.core.model.loader.xml.tree;

import org.w3c.dom.Element;

import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.loader.IResourceEMLoadContext;
import com.forms.beneform4j.excel.core.model.loader.xml.IEMTopElementParser;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoaderConsts;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 解析XML配置中的<tree-workbook><br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class TreeWorkbookParser implements IEMTopElementParser {

    @Override
    public void parse(IResourceEMLoadContext context, Element element) {
        String modelId = element.getAttribute(XmlEMLoaderConsts.ID_PROPERTY);
        IEM em = TreeWorkbookParserDelegate.parseWorkbookElement(modelId, element);
        context.register(em);
    }

}
