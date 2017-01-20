package com.forms.beneform4j.excel.core.model.loader.xml;

import org.w3c.dom.Element;

import com.forms.beneform4j.excel.core.model.loader.IResourceEMLoadContext;

public interface IEMTopElementParser {

    public void parse(IResourceEMLoadContext context, Element element);
}
