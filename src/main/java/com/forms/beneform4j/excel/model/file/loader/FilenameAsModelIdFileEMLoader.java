package com.forms.beneform4j.excel.model.file.loader;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;

import com.forms.beneform4j.excel.model.base.EMType;
import com.forms.beneform4j.excel.model.base.em.BaseEM;
import com.forms.beneform4j.excel.model.base.loader.AbstractResourceEMLoader;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 将Resource对象作为一个Excel模型，并且将文件名称作为模型ID的Excel文件模型加载器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2016-12-22<br>
 */
public class FilenameAsModelIdFileEMLoader extends AbstractResourceEMLoader {

    @Override
    protected void registerExcelModel(Resource resource, boolean force) {
        String filename = resource.getFilename();
        String type = EMType.getTypeByFilename(filename);
        BaseEM em = ResourceEMLoaderHelper.newResourceEM(resource, type);
        String id = FilenameUtils.getBaseName(filename);
        em.setId(id);
        em.setName(id);
        em.setDesc(resource.getDescription());
        addEM(em, force);
    }
}
