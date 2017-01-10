package com.forms.beneform4j.excel.model.dynamic.em;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;

import com.forms.beneform4j.core.util.exception.Throw;
<<<<<<< HEAD
import com.forms.beneform4j.core.util.xml.context.IXmlParserContext.XmlValidationMode;
import com.forms.beneform4j.core.util.xml.context.impl.XmlParserContext;
import com.forms.beneform4j.core.util.xml.parser.XmlParserUtils;
import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.model.base.em.BaseEM;
import com.forms.beneform4j.excel.model.dynamic.IDynamicTreeEM;
import com.forms.beneform4j.excel.model.tree.ITreeEM;
import com.forms.beneform4j.excel.model.tree.loader.xml.XmlTreeEMParserDelegate;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class ResourceFreemarkerTreeEM extends BaseEM implements IDynamicTreeEM {

    /**
     * 
     */
    private static final long serialVersionUID = 6001561627552787459L;

    private static final ThreadLocal<Configuration> config = new ThreadLocal<Configuration>() {
        @Override
        protected Configuration initialValue() {
            Configuration conf = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            conf.setLocalizedLookup(false);
            return conf;
        }
    };

    private final Resource resource;

    public ResourceFreemarkerTreeEM(Resource resource) {
        this.resource = resource;
    }

    @Override
    public ITreeEM apply(Object param, Object data) {
        String xmlString = getXmlString(param, data);
        Resource resource = new ByteArrayResource(xmlString.getBytes());
        XmlParserContext context = new XmlParserContext();
        context.setXmlValidationMode(XmlValidationMode.NONE);
        Document document = XmlParserUtils.buildDocument(context, resource);
        return XmlTreeEMParserDelegate.parseWorkbookDocument(getId(), document);
    }

    private String getXmlString(Object param, Object data) {
        try {
            Configuration conf = config.get();
            conf.setTemplateLoader(new ResourceTemplateLoader(resource));
            Template template = conf.getTemplate("resourceTemplate");
            conf.setSharedVariable(ExcelComponentConfig.getParamObjectVarName(), param);
            conf.setSharedVariable(ExcelComponentConfig.getDataObjectVarName(), data);
            StringWriter result = new StringWriter();
            template.process(param, result);
=======
import com.forms.beneform4j.core.util.xml.parser.XmlParserUtils;
import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.model.base.em.BaseEM;
import com.forms.beneform4j.excel.model.dynamic.IDynamicTreeEM;
import com.forms.beneform4j.excel.model.tree.ITreeEM;
import com.forms.beneform4j.excel.model.tree.loader.xml.XmlTreeEMParserDelegate;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class ResourceFreemarkerTreeEM extends BaseEM implements IDynamicTreeEM {

    /**
     * 
     */
    private static final long serialVersionUID = 6001561627552787459L;

    private static final ThreadLocal<Configuration> config = new ThreadLocal<Configuration>() {
        @Override
        protected Configuration initialValue() {
            Configuration conf = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            conf.setLocalizedLookup(false);
            return conf;
        }
    };

    private final Resource resource;

    public ResourceFreemarkerTreeEM(Resource resource) {
        this.resource = resource;
    }

    @Override
    public ITreeEM apply(Object param, Object data) {
        String xmlString = getXmlString(param, data);
        Resource resource = new ByteArrayResource(xmlString.getBytes());
        Document document = XmlParserUtils.buildDocument(resource);
        return XmlTreeEMParserDelegate.parseWorkbookDocument(getId(), document);
    }

    private String getXmlString(Object param, Object data) {
        try {
            Configuration conf = config.get();
            conf.setTemplateLoader(new ResourceTemplateLoader(resource));
            Template template = conf.getTemplate("resourceTemplate");
            conf.setSharedVariable(ExcelComponentConfig.getParamObjectVarName(), param);
            StringWriter result = new StringWriter();
            template.process(data, result);
>>>>>>> branch 'master' of http://192.168.22.190:8090/beneform4j/beneform4j-excel.git
            return result.toString();
        } catch (Exception e) {
            throw Throw.createRuntimeException(e);
        }
    }

    private class ResourceTemplateLoader implements TemplateLoader {

        private final Resource resource;

        public ResourceTemplateLoader(Resource resource) {
            this.resource = resource;
        }

        @Override
        public Object findTemplateSource(String name) throws IOException {
            return (resource.exists() ? resource : null);
        }

        @Override
        public Reader getReader(Object templateSource, String encoding) throws IOException {
            Resource resource = (Resource) templateSource;
            try {
                return new InputStreamReader(resource.getInputStream(), encoding);
            } catch (IOException ex) {
                throw Throw.createRuntimeException(ex);
            }
        }

        @Override
        public long getLastModified(Object templateSource) {
            Resource resource = (Resource) templateSource;
            try {
                return resource.lastModified();
            } catch (IOException ex) {
                return -1;
            }
        }

        @Override
        public void closeTemplateSource(Object templateSource) throws IOException {}

    }
}
