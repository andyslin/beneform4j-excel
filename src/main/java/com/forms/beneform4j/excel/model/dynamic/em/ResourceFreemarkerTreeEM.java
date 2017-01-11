package com.forms.beneform4j.excel.model.dynamic.em;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;

import com.forms.beneform4j.core.util.xml.context.IXmlParserContext.XmlValidationMode;
import com.forms.beneform4j.core.util.xml.context.impl.XmlParserContext;
import com.forms.beneform4j.core.util.xml.parser.XmlParserUtils;
import com.forms.beneform4j.excel.model.base.em.BaseEM;
import com.forms.beneform4j.excel.model.dynamic.IDynamicTreeEM;
import com.forms.beneform4j.excel.model.tree.ITreeEM;
import com.forms.beneform4j.excel.model.tree.loader.xml.XmlTreeEMParserDelegate;

public class ResourceFreemarkerTreeEM extends BaseEM implements IDynamicTreeEM {

    /**
     * 
     */
    private static final long serialVersionUID = 6001561627552787459L;

    private final Resource resource;

    public ResourceFreemarkerTreeEM(Resource resource) {
        this.resource = resource;
    }

    @Override
    public ITreeEM apply(Object param, Object data) {
        String xmlString = FreemarkerUtils.process(resource, param, data);
        Resource resource = new ByteArrayResource(xmlString.getBytes());
        XmlParserContext context = new XmlParserContext();
        context.setXmlValidationMode(XmlValidationMode.NONE);
        Document document = XmlParserUtils.buildDocument(context, resource);
        return XmlTreeEMParserDelegate.parseWorkbookDocument(getId(), document);
    }
}
