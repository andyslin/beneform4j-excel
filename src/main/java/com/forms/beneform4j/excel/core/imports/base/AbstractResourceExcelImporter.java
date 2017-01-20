package com.forms.beneform4j.excel.core.imports.base;

import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.core.imports.stream.IWorkbookStreamHandler;
import com.forms.beneform4j.excel.core.imports.stream.WorkbookStreamUtils;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEM;

public abstract class AbstractResourceExcelImporter extends AbstractModelExcelImporter {

    abstract protected Object doImports(Resource resource, IBeanEM model);

    protected void doImports(Resource resource, IWorkbookStreamHandler handler) {
        WorkbookStreamUtils.parse(resource, handler);
    }

    @Override
    public void imports(InputStream input, final IWorkbookStreamHandler handler) {
        importsWithResource(input, new ResourceCallback<Object>() {
            @Override
            protected Object callback(Resource resource) {
                doImports(resource, handler);
                return null;
            }
        });
    }

    @Override
    public void imports(String location, final IWorkbookStreamHandler handler) {
        importsWithResource(location, new ResourceCallback<Object>() {
            @Override
            protected Object callback(Resource resource) {
                doImports(resource, handler);
                return null;
            }
        });
    }

    @Override
    protected Object doImports(InputStream input, final IBeanEM model) {
        return importsWithResource(input, new ResourceCallback<Object>() {
            @Override
            protected Object callback(Resource resource) {
                return doImports(resource, model);
            }
        });
    }

    @Override
    protected Object doImports(String location, final IBeanEM model) {
        return importsWithResource(location, new ResourceCallback<Object>() {
            @Override
            protected Object callback(Resource resource) {
                return doImports(resource, model);
            }
        });
    }

    private <T> T importsWithResource(String location, ResourceCallback<T> callback) {
        Resource resource = CoreUtils.getResource(location);
        return callback.callback(resource);
    }

    private <T> T importsWithResource(InputStream input, ResourceCallback<T> callback) {
        Resource resource = new InputStreamResource(input);
        return callback.callback(resource);
    }

    private abstract class ResourceCallback<T> {
        abstract protected T callback(Resource resource);
    }
}
