package com.forms.beneform4j.excel.exports.tree;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.data.accessor.DataAccessors;
import com.forms.beneform4j.excel.data.accessor.IDataAccessor;
import com.forms.beneform4j.excel.exports.base.AbstractWorkbookExcelExporter;
import com.forms.beneform4j.excel.model.base.IEM;
import com.forms.beneform4j.excel.model.dynamic.IDynamicTreeEM;
import com.forms.beneform4j.excel.model.tree.ITreeEM;

public abstract class AbstractTreeEMExcelExporter extends AbstractWorkbookExcelExporter {

    abstract protected void export(ITreeEM model, IDataAccessor accessor, Workbook workbook);

    @Override
    protected Workbook newWorkbook(IEM model, Object param, Object data) {
        return new SXSSFWorkbook(1000);
    }

    @Override
    protected void export(IEM model, Object param, Object data, Workbook workbook) {
        ITreeEM tem = null;
        if (model instanceof ITreeEM) {
            tem = (ITreeEM) model;
        } else if (model instanceof IDynamicTreeEM) {
            tem = ((IDynamicTreeEM) model).apply(param, data);
        } else {
            Throw.throwRuntimeException("不支持的Excel模型");
        }
        IDataAccessor accessor = getDataAccessor(param, data);
        export(tem, accessor, workbook);
    }

    private IDataAccessor getDataAccessor(Object param, Object data) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ExcelComponentConfig.getParamObjectVarName(), param);
        IDataAccessor accessor = DataAccessors.newDataAccessor(data, map);
        return accessor;
    }
}
