package com.forms.beneform4j.excel.core.model.em.file.impl;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.model.em.base.BaseEM;
import com.forms.beneform4j.excel.core.model.em.file.IFileEM;

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

    @Override
    public InputStream getInputStream() {
        try {
            return this.resource.getInputStream();
        } catch (IOException e) {
            throw Throw.createRuntimeException("读取文件模型异常", e);
        }
    }

    @Override
    public String getFilename() {
        return this.resource.getFilename();
    }
}
