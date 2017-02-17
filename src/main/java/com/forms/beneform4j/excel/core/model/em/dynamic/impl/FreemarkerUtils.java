package com.forms.beneform4j.excel.core.model.em.dynamic.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import org.springframework.core.io.Resource;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Freemarker模板工具类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
/* package */ class FreemarkerUtils {

    private static final ThreadLocal<Configuration> config = new ThreadLocal<Configuration>() {
        @Override
        protected Configuration initialValue() {
            Configuration conf = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            conf.setLocalizedLookup(false);
            return conf;
        }
    };

    /* package */ static String process(Resource resource, Object param, Object data) {
        try {
            Configuration conf = config.get();
            conf.setTemplateLoader(new ResourceTemplateLoader(resource));
            Template template = conf.getTemplate("resourceTemplate");
            if (null != param) {
                conf.setSharedVariable(ExcelComponentConfig.getParamObjectVarName(), param);
            }
            conf.setSharedVariable(ExcelComponentConfig.getDataObjectVarName(), data);
            StringWriter result = new StringWriter();
            template.process(param, result);
            return result.toString();
        } catch (Exception e) {
            throw Throw.createRuntimeException(ExcelExceptionCodes.BF0XLS10, e, resource);
        }
    }

    private static class ResourceTemplateLoader implements TemplateLoader {

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
                throw Throw.createRuntimeException(ExcelExceptionCodes.BF0XLS10, ex, resource);
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
