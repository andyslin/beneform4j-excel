package com.forms.beneform4j.excel.exports;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.data.accessor.DataAccessors;
import com.forms.beneform4j.excel.data.accessor.IDataAccessor;
import com.forms.beneform4j.excel.data.loader.IDataLoader;
import com.forms.beneform4j.excel.data.loader.impl.ParamAsDataDataLoader;
import com.forms.beneform4j.excel.model.base.IEM;

public abstract class AbstractAccessorExcelExporter extends AbstractStreamExcelExporter {

    private static final IDataLoader DEFAULT_LOADER = new ParamAsDataDataLoader();

    abstract protected void export(IEM model, IDataAccessor accessor, OutputStream output);

    @Override
    public void export(IEM model, Object data, OutputStream output) {
        this.export(model, DEFAULT_LOADER, data, output);
    }

    @Override
    public void export(IEM model, IDataLoader loader, Object param, OutputStream output) {
        Object data = loader.load(param);
        IDataAccessor accessor = getDataAccessor(param, data);
        this.export(model, accessor, output);
    }

    private IDataAccessor getDataAccessor(Object param, Object data) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ExcelComponentConfig.getParamObjectVarName(), param);
        IDataAccessor accessor = DataAccessors.newDataAccessor(data, map);
        return accessor;
    }
}
