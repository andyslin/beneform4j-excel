package com.forms.beneform4j.excel.exports.file;

import java.io.OutputStream;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.data.accessor.IDataAccessor;
import com.forms.beneform4j.excel.exports.AbstractAccessorExcelExporter;
import com.forms.beneform4j.excel.model.base.IEM;
import com.forms.beneform4j.excel.model.file.IFileEM;

public abstract class AbstractFileEMExcelExporter extends AbstractAccessorExcelExporter {

    abstract protected void export(IFileEM model, IDataAccessor accessor, OutputStream output);

    @Override
    protected void export(IEM model, IDataAccessor accessor, OutputStream output) {
        IFileEM fem = null;
        if (model instanceof IFileEM) {
            fem = (IFileEM) model;
        } else {
            Throw.throwRuntimeException("不支持的Excel模型");
        }
        this.export(fem, accessor, output);
    }
}
