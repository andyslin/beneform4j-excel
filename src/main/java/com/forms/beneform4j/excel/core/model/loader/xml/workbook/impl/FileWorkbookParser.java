package com.forms.beneform4j.excel.core.model.loader.xml.workbook.impl;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.config.BaseConfig;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.model.em.EMType;
import com.forms.beneform4j.excel.core.model.em.base.BaseEM;
import com.forms.beneform4j.excel.core.model.loader.IResourceEMLoadContext;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoader;

public class FileWorkbookParser extends AbstractFileWorkbookParser {

    private static final String NAME = "name";
    private static final String DESC = "desc";
    private static final String PRIOR = "prior";
    private static final String LOCATION = "location";

    @Override
    public void parse(IResourceEMLoadContext context, Element ele) {
        String location = ele.getAttribute(LOCATION);
        if (CoreUtils.isBlank(location)) {
            Throw.throwRuntimeException("资源位置不能为空");
        }

        ResourcePatternResolver loader = BaseConfig.getResourcePatternResolver();
        Resource resource = loader.getResource(location);
        if (null == resource || !resource.exists()) {
            Throw.throwRuntimeException("资源不存在:" + location);
        }

        String filename = resource.getFilename();
        EMType type = EMType.getTypeByFilename(filename);
        BaseEM em = getBaseWorkbookEM(resource, type);

        String id = ele.getAttribute(XmlEMLoader.WORKBOOK_ID_PROPERTY);
        if (CoreUtils.isBlank(id)) {
            id = resolveFileWorkbookId(location, filename);
        }
        em.setId(id);

        String name = ele.getAttribute(NAME);
        if (CoreUtils.isBlank(name)) {
            name = filename;
        }
        em.setName(name);

        String desc = ele.getAttribute(DESC);
        if (CoreUtils.isBlank(desc)) {
            desc = resource.getDescription();
        }
        em.setDesc(desc);

        int prior = 0;
        String priorStr = ele.getAttribute(PRIOR);
        if (!CoreUtils.isBlank(priorStr)) {
            prior = Integer.parseInt(priorStr);
        }
        em.setPrior(prior);

        context.register(em);
    }
}
