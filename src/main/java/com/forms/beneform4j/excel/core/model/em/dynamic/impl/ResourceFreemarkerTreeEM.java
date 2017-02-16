package com.forms.beneform4j.excel.core.model.em.dynamic.impl;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;

import com.forms.beneform4j.core.util.xml.context.IXmlParserContext.XmlValidationMode;
import com.forms.beneform4j.core.util.xml.context.impl.XmlParserContext;
import com.forms.beneform4j.core.util.xml.parser.XmlParserUtils;
import com.forms.beneform4j.excel.core.model.em.base.BaseEM;
import com.forms.beneform4j.excel.core.model.em.dynamic.IDynamicTreeEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEM;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.TreeWorkbookParserDelegate;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 使用Freemarker模板实现的动态树型配置模型<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class ResourceFreemarkerTreeEM extends BaseEM implements IDynamicTreeEM {

    /**
     * 
     */
    private static final long serialVersionUID = 6001561627552787459L;

    private final Resource resource;

    public ResourceFreemarkerTreeEM(Resource resource) {
        this.resource = resource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITreeEM apply(Object param, Object data) {
        String xmlString = FreemarkerUtils.process(resource, param, data);
        Resource resource = new ByteArrayResource(xmlString.getBytes());
        XmlParserContext context = new XmlParserContext();
        //由于不要求配置id，这里使用弱化的校验模式
        context.setXmlValidationMode(XmlValidationMode.NONE);
        Document document = XmlParserUtils.buildDocument(context, resource);
        return TreeWorkbookParserDelegate.parseWorkbookDocument(getId(), document);
    }
}
