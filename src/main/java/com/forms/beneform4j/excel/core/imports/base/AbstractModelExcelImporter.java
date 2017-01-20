package com.forms.beneform4j.excel.core.imports.base;

import java.io.InputStream;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.imports.IExcelImporter;
import com.forms.beneform4j.excel.core.model.em.EMManager;
import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEM;

public abstract class AbstractModelExcelImporter implements IExcelImporter {

    abstract protected Object doImports(InputStream input, IBeanEM model);

    abstract protected Object doImports(String location, IBeanEM model);

    @Override
    public <T> T imports(InputStream input, Class<T> cls) {
        IBeanEM model = resolveBeanEM(null, null, cls);
        Object rs = this.doImports(input, model);
        return CoreUtils.convert(rs, cls);
    }

    @Override
    public <T> T imports(String location, Class<T> cls) {
        IBeanEM model = resolveBeanEM(null, null, cls);
        Object rs = this.doImports(location, model);
        return CoreUtils.convert(rs, cls);
    }

    @Override
    public Object imports(InputStream input, String modelId) {
        IBeanEM model = resolveBeanEM(null, modelId, null);
        return this.doImports(input, model);
    }

    @Override
    public Object imports(String location, String modelId) {
        IBeanEM model = resolveBeanEM(null, modelId, null);
        return this.doImports(location, model);
    }

    @Override
    public <T> T imports(InputStream input, String modelId, Class<T> cls) {
        IBeanEM model = resolveBeanEM(null, modelId, cls);
        Object rs = this.doImports(input, model);
        return CoreUtils.convert(rs, cls);
    }

    @Override
    public <T> T imports(String location, String modelId, Class<T> cls) {
        IBeanEM model = resolveBeanEM(null, modelId, cls);
        Object rs = this.doImports(location, model);
        return CoreUtils.convert(rs, cls);
    }

    @Override
    public Object imports(InputStream input, IEM model) {
        IBeanEM beanEM = resolveBeanEM(model, null, null);
        return this.doImports(input, beanEM);
    }

    @Override
    public Object imports(String location, IEM model) {
        IBeanEM beanEM = resolveBeanEM(model, null, null);
        return this.doImports(location, beanEM);
    }

    private IBeanEM resolveBeanEM(IEM em, String modelId, Class<?> cls) {
        if (null == em) {
            if (!CoreUtils.isBlank(modelId)) {
                em = EMManager.load(modelId);
            }

            if (null == em && null != cls) {
                em = EMManager.load(cls.getName());
            }
        }

        if (null == em) {
            Throw.throwRuntimeException("Excel模型为空");
        } else if (!(em instanceof IBeanEM)) {
            Throw.throwRuntimeException("不支持的Excel模型：" + em);
        }
        return (IBeanEM) em;
    }
}
