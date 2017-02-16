package com.forms.beneform4j.excel.core.model.loader.xml.file;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;

import com.forms.beneform4j.excel.core.model.em.EMType;
import com.forms.beneform4j.excel.core.model.em.base.BaseEM;
import com.forms.beneform4j.excel.core.model.em.dynamic.impl.ResourceFreemarkerTreeEM;
import com.forms.beneform4j.excel.core.model.em.file.impl.ResourceFileEM;
import com.forms.beneform4j.excel.core.model.loader.xml.IEMTopElementParser;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 文件模型配置元素的抽象解析器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public abstract class AbstractFileWorkbookParser implements IEMTopElementParser {

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

    protected EMType getEMTypeByFilename(String filename) {
        String suffix = FilenameUtils.getExtension(filename);
        if ("xls".equalsIgnoreCase(suffix) || "xlsx".equalsIgnoreCase(suffix)) {
            return EMType.EXCEL;
        } else {
            return EMType.FREEMARKER_TREE;
        }
    }
}
