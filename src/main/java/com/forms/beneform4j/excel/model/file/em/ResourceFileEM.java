package com.forms.beneform4j.excel.model.file.em;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;

import com.forms.beneform4j.excel.model.base.em.BaseEM;
import com.forms.beneform4j.excel.model.file.IFileEm;

public class ResourceFileEM extends BaseEM implements IFileEm {

    /**
     * 
     */
    private static final long serialVersionUID = -3306555221207908677L;

    private final Resource resource;

    public ResourceFileEM(Resource resource) {
        super();
        this.resource = resource;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.resource.getInputStream();
    }

    @Override
    public String toString() {
        return "IFileEm[id=" + getId() + "] " + resource.getDescription();
    }
}
