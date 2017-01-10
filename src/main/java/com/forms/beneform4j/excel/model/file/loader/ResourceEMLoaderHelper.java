package com.forms.beneform4j.excel.model.file.loader;

import org.springframework.core.io.Resource;

import com.forms.beneform4j.excel.model.base.EMType;
import com.forms.beneform4j.excel.model.base.em.BaseEM;
import com.forms.beneform4j.excel.model.dynamic.em.ResourceFreemarkerTreeEM;
import com.forms.beneform4j.excel.model.file.em.ResourceFileEM;

/* package */class ResourceEMLoaderHelper {

    /* package */ static BaseEM newResourceEM(Resource resource, String type) {
        BaseEM em = null;
        if (EMType.MODEL_TYPE_EXCEL.equals(type)) {
            em = new ResourceFileEM(resource);
            em.setType(type);
        } else if (EMType.MODEL_TYPE_FREEMARKER.equals(type)) {
            em = new ResourceFreemarkerTreeEM(resource);
            em.setType(type);
        } else {

        }

        return em;
    }
}
