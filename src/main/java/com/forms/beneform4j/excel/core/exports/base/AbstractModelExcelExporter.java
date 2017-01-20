package com.forms.beneform4j.excel.core.exports.base;

import java.io.OutputStream;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.exports.IExcelExporter;
import com.forms.beneform4j.excel.core.model.em.EMManager;
import com.forms.beneform4j.excel.core.model.em.IEM;

public abstract class AbstractModelExcelExporter implements IExcelExporter {

    @Override
    public void exports(String modelId, final Object data, final OutputStream output) {
        exportsInModel(modelId, new ExportModelCallback() {
            @Override
            protected String callback(IEM model) {
                exports(model, data, output);
                return null;
            }
        });
    }

    @Override
    public String exports(String modelId, final Object data, final String filename) {
        return exportsInModel(modelId, new ExportModelCallback() {
            @Override
            protected String callback(IEM model) {
                return exports(model, data, filename);
            }
        });
    }

    @Override
    public void exports(String modelId, final Object param, final Object data, final OutputStream output) {
        exportsInModel(modelId, new ExportModelCallback() {
            @Override
            protected String callback(IEM model) {
                exports(model, param, data, output);
                return null;
            }
        });
    }

    @Override
    public String exports(String modelId, final Object param, final Object data, final String filename) {
        return exportsInModel(modelId, new ExportModelCallback() {
            @Override
            protected String callback(IEM model) {
                return exports(model, param, data, filename);
            }
        });
    }

    private String exportsInModel(String modelId, ExportModelCallback callback) {
        IEM model = EMManager.load(modelId);
        if (null == model) {
            Throw.throwRuntimeException("没找到Excel模型:" + modelId);
        }
        return callback.callback(model);
    }

    private abstract class ExportModelCallback {
        abstract protected String callback(IEM model);
    }
}
