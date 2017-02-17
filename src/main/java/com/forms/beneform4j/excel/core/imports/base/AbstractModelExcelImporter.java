package com.forms.beneform4j.excel.core.imports.base;

import java.io.InputStream;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.imports.IExcelImporter;
import com.forms.beneform4j.excel.core.model.em.EMManager;
import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEM;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 抽象的Excel导入实现类，实现加载Excel模型的功能<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public abstract class AbstractModelExcelImporter implements IExcelImporter {

    abstract protected Object doImports(InputStream input, IEM model);

    abstract protected Object doImports(String location, IEM model);

    @Override
    public <T> T imports(final InputStream input, Class<T> cls) {
        Object rs = this.importsInModel(null, null, cls, new ImportModelCallback<Object>() {
            @Override
            protected Object callback(IEM model) {
                return doImports(input, model);
            }
        });
        return CoreUtils.convert(rs, cls);
    }

    @Override
    public <T> T imports(final String location, Class<T> cls) {
        Object rs = this.importsInModel(null, null, cls, new ImportModelCallback<Object>() {
            @Override
            protected Object callback(IEM model) {
                return doImports(location, model);
            }
        });
        return CoreUtils.convert(rs, cls);
    }

    @Override
    public Object imports(final InputStream input, String modelId) {
        return this.importsInModel(null, modelId, null, new ImportModelCallback<Object>() {
            @Override
            protected Object callback(IEM model) {
                return doImports(input, model);
            }
        });
    }

    @Override
    public Object imports(final String location, String modelId) {
        return this.importsInModel(null, modelId, null, new ImportModelCallback<Object>() {
            @Override
            protected Object callback(IEM model) {
                return doImports(location, model);
            }
        });
    }

    @Override
    public <T> T imports(final InputStream input, String modelId, Class<T> cls) {
        Object rs = this.importsInModel(null, modelId, cls, new ImportModelCallback<Object>() {
            @Override
            protected Object callback(IEM model) {
                return doImports(input, model);
            }
        });
        return CoreUtils.convert(rs, cls);
    }

    @Override
    public <T> T imports(final String location, String modelId, Class<T> cls) {
        Object rs = this.importsInModel(null, modelId, cls, new ImportModelCallback<Object>() {
            @Override
            protected Object callback(IEM model) {
                return doImports(location, model);
            }
        });
        return CoreUtils.convert(rs, cls);
    }

    @Override
    public Object imports(final InputStream input, IEM model) {
        return this.importsInModel(model, null, null, new ImportModelCallback<Object>() {
            @Override
            protected Object callback(IEM model) {
                return doImports(input, model);
            }
        });
    }

    @Override
    public Object imports(final String location, IEM model) {
        return this.importsInModel(model, null, null, new ImportModelCallback<Object>() {
            @Override
            protected Object callback(IEM model) {
                return doImports(location, model);
            }
        });
    }

    private <T> T importsInModel(IEM em, String modelId, Class<?> cls, ImportModelCallback<T> callback) {
        if (null == em) {
            if (!CoreUtils.isBlank(modelId)) {
                em = EMManager.load(modelId);
            }

            if (null == em && null != cls) {
                em = EMManager.load(cls.getName());
            }
        }

        if (null == em) {
            Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS07);
        } else if (null != cls && !String.class.isAssignableFrom(cls) && !(em instanceof IBeanEM)) {//返回的不是字符串，并且不是Bean模型
            Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS08, em);
        }
        return callback.callback(em);
    }

    private abstract class ImportModelCallback<T> {
        abstract protected T callback(IEM model);
    }
}
