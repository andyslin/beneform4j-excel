package com.forms.beneform4j.excel.core.exports.base;

import java.io.OutputStream;

import com.forms.beneform4j.excel.core.data.loader.IDataLoader;
import com.forms.beneform4j.excel.core.model.em.IEM;

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
}
