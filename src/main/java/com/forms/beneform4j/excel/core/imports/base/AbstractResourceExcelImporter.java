package com.forms.beneform4j.excel.core.imports.base;

import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.core.imports.stream.IWorkbookStreamHandler;
import com.forms.beneform4j.excel.core.imports.stream.WorkbookStreamUtils;
import com.forms.beneform4j.excel.core.model.em.IEM;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 抽象的Excel导入实现类，将输入流或资源位置统一转换成资源对象后再导入<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public abstract class AbstractResourceExcelImporter extends AbstractModelExcelImporter {

    abstract protected Object doImports(Resource resource, IEM model);

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
    protected Object doImports(InputStream input, final IEM model) {
        return importsWithResource(input, new ResourceCallback<Object>() {
            @Override
            protected Object callback(Resource resource) {
                return doImports(resource, model);
            }
        });
    }

    @Override
    protected Object doImports(String location, final IEM model) {
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
