package com.forms.beneform4j.excel.core.model.loader.xml.tree;

import org.w3c.dom.Element;

import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.loader.IResourceEMLoadContext;
import com.forms.beneform4j.excel.core.model.loader.xml.IEMTopElementParser;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoaderConsts;

public class TreeWorkbookParser implements IEMTopElementParser {

    @Override
    public void parse(IResourceEMLoadContext context, Element element) {
        String modelId = element.getAttribute(XmlEMLoaderConsts.ID_PROPERTY);
        IEM em = TreeWorkbookParserDelegate.parseWorkbookElement(modelId, element);
        context.register(em);
    }

}
