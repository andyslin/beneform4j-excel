package com.forms.beneform4j.excel.exports.base;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.data.accessor.DataAccessors;
import com.forms.beneform4j.excel.data.accessor.IDataAccessor;
import com.forms.beneform4j.excel.data.loader.IDataLoader;
import com.forms.beneform4j.excel.model.base.IEM;

public abstract class AbstractDataExcelExporter extends AbstractStreamExcelExporter {

    @Override
    public void export(IEM model, Object data, OutputStream output) {
        this.export(model, data, data, output);
    }

    @Override
    public void export(IEM model, Object param, IDataLoader loader, OutputStream output) {
        Object data = loader.load(param);
        this.export(model, param, data, output);
    }

    protected IDataAccessor getDataAccessor(Object param, Object data) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ExcelComponentConfig.getParamObjectVarName(), param);
        IDataAccessor accessor = DataAccessors.newDataAccessor(data, map);
        return accessor;
    }
}
