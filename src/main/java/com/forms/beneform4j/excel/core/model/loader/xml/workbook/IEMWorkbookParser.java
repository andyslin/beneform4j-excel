package com.forms.beneform4j.excel.core.model.loader.xml.workbook;

import org.w3c.dom.Element;

import com.forms.beneform4j.excel.core.model.loader.IResourceEMLoadContext;

public interface IEMWorkbookParser {

    public void parse(IResourceEMLoadContext context, Element workbook);
}
