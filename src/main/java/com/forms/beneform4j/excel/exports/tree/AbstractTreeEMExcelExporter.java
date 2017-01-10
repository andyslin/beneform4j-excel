package com.forms.beneform4j.excel.exports.tree;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.forms.beneform4j.core.util.exception.Throw;
<<<<<<< HEAD
import com.forms.beneform4j.excel.data.accessor.IDataAccessor;
import com.forms.beneform4j.excel.exports.base.AbstractDataExcelExporter;
import com.forms.beneform4j.excel.model.base.IEM;
import com.forms.beneform4j.excel.model.dynamic.IDynamicTreeEM;
import com.forms.beneform4j.excel.model.tree.ITreeEM;

public abstract class AbstractTreeEMExcelExporter extends AbstractDataExcelExporter {

    abstract protected Workbook export(ITreeEM model, IDataAccessor accessor);

    @Override
    public void export(IEM model, Object param, Object data, OutputStream output) {
        ITreeEM tem = null;
        if (model instanceof ITreeEM) {
            tem = (ITreeEM) model;
        } else if (model instanceof IDynamicTreeEM) {
            tem = ((IDynamicTreeEM) model).apply(param, data);
        } else {
            Throw.throwRuntimeException("不支持的Excel模型");
        }
        IDataAccessor accessor = getDataAccessor(param, data);
=======
import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.data.accessor.IDataAccessor;
import com.forms.beneform4j.excel.exports.AbstractAccessorExcelExporter;
import com.forms.beneform4j.excel.model.base.IEM;
import com.forms.beneform4j.excel.model.dynamic.IDynamicTreeEM;
import com.forms.beneform4j.excel.model.tree.ITreeEM;

public abstract class AbstractTreeEMExcelExporter extends AbstractAccessorExcelExporter {

    abstract protected Workbook export(ITreeEM model, IDataAccessor accessor);

    @Override
    protected void export(IEM model, IDataAccessor accessor, OutputStream output) {
        ITreeEM tem = null;
        if (model instanceof ITreeEM) {
            tem = (ITreeEM) model;
        } else if (model instanceof IDynamicTreeEM) {
            Object data = accessor.getRoot();
            Object param = accessor.value(ExcelComponentConfig.getParamObjectVarName());
            tem = ((IDynamicTreeEM) model).apply(param, data);
        } else {
            Throw.throwRuntimeException("不支持的Excel模型");
        }
>>>>>>> branch 'master' of http://192.168.22.190:8090/beneform4j/beneform4j-excel.git
        Workbook workbook = this.export(tem, accessor);
        writeOutputStream(workbook, output);
    }

    private void writeOutputStream(Workbook workbook, OutputStream output) {
        if (null != workbook) {
            try {
                workbook.write(output);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (workbook instanceof SXSSFWorkbook) {
                    ((SXSSFWorkbook) workbook).dispose();
                }
            }
        }
    }
}
