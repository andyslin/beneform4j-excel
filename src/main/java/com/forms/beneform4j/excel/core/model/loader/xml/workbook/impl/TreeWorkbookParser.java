package com.forms.beneform4j.excel.core.model.loader.xml.workbook.impl;

import org.w3c.dom.Element;

import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.loader.IResourceEMLoadContext;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoader;
import com.forms.beneform4j.excel.core.model.loader.xml.workbook.IEMWorkbookParser;

public class TreeWorkbookParser implements IEMWorkbookParser {

    @Override
    public void parse(IResourceEMLoadContext context, Element workbook) {
        String modelId = workbook.getAttribute(XmlEMLoader.WORKBOOK_ID_PROPERTY);
        IEM em = TreeWorkbookParserDelegate.parseWorkbookElement(modelId, workbook);
        context.register(em);
    }

}
