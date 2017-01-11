package com.forms.beneform4j.excel.exports.base;

import java.io.OutputStream;

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
}
