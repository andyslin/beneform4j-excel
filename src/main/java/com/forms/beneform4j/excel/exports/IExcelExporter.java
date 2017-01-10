package com.forms.beneform4j.excel.exports;

import java.io.OutputStream;

import com.forms.beneform4j.excel.data.loader.IDataLoader;
import com.forms.beneform4j.excel.model.base.IEM;

public interface IExcelExporter {

    public void export(IEM model, Object data, OutputStream output);

    public void export(IEM model, Object data, String filename);

    public void export(IEM model, IDataLoader loader, Object param, OutputStream output);

    public void export(IEM model, IDataLoader loader, Object param, String filename);
}
