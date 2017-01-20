package com.forms.beneform4j.excel.core.exports.base;

import java.io.OutputStream;

import com.forms.beneform4j.excel.core.model.em.IEM;

public abstract class AbstractDataExcelExporter extends AbstractOutputStreamExcelExporter {

    @Override
    public void exports(IEM model, Object data, OutputStream output) {
        this.exports(model, data, data, output);
    }
}
