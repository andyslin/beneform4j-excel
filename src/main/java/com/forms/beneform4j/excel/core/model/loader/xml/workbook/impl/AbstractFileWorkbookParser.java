package com.forms.beneform4j.excel.core.model.loader.xml.workbook.impl;

import org.springframework.core.io.Resource;

import com.forms.beneform4j.excel.core.model.em.EMType;
import com.forms.beneform4j.excel.core.model.em.base.BaseEM;
import com.forms.beneform4j.excel.core.model.em.dynamic.impl.ResourceFreemarkerTreeEM;
import com.forms.beneform4j.excel.core.model.em.file.impl.ResourceFileEM;
import com.forms.beneform4j.excel.core.model.loader.xml.workbook.IEMWorkbookParser;

public abstract class AbstractFileWorkbookParser implements IEMWorkbookParser {

    protected String resolveFileWorkbookId(String location, String filename) {
        int index = filename.indexOf(".");
        if (-1 == index) {
            return filename;
        } else {
            return filename.substring(0, index);
        }
    }

    protected BaseEM getBaseWorkbookEM(Resource resource, EMType type) {
        BaseEM em = null;
        if (EMType.FREEMARKER_TREE.equals(type)) {
            em = new ResourceFreemarkerTreeEM(resource);
        } else {
            em = new ResourceFileEM(resource);
        }
        em.setType(type);
        return em;
    }
}
