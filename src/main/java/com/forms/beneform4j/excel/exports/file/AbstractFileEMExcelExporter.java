package com.forms.beneform4j.excel.exports.file;

import java.io.OutputStream;

import com.forms.beneform4j.core.util.exception.Throw;
<<<<<<< HEAD
import com.forms.beneform4j.excel.exports.base.AbstractDataExcelExporter;
import com.forms.beneform4j.excel.model.base.IEM;
import com.forms.beneform4j.excel.model.file.IFileEM;

public abstract class AbstractFileEMExcelExporter extends AbstractDataExcelExporter {

    abstract protected void export(IFileEM model, Object param, Object data, OutputStream output);

    @Override
    public void export(IEM model, Object param, Object data, OutputStream output) {
        IFileEM fem = null;
        if (model instanceof IFileEM) {
            fem = (IFileEM) model;
        } else {
            Throw.throwRuntimeException("不支持的Excel模型");
        }
        this.export(fem, param, data, output);
=======
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
>>>>>>> branch 'master' of http://192.168.22.190:8090/beneform4j/beneform4j-excel.git
    }
}
