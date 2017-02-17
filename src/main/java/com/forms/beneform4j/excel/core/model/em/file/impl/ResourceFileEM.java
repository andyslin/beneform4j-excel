package com.forms.beneform4j.excel.core.model.em.file.impl;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.model.em.base.BaseEM;
import com.forms.beneform4j.excel.core.model.em.file.IFileEM;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 使用Spring中资源对象实现的文件模型配置实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class ResourceFileEM extends BaseEM implements IFileEM {

    /**
     * 
     */
    private static final long serialVersionUID = -3306555221207908677L;

    private final Resource resource;

    public ResourceFileEM(Resource resource) {
        super();
        this.resource = resource;
        // 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getInputStream() {
        try {
            return this.resource.getInputStream();
        } catch (IOException e) {
            throw Throw.createRuntimeException(ExcelExceptionCodes.BF0XLS01, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFilename() {
        return this.resource.getFilename();
    }
}
