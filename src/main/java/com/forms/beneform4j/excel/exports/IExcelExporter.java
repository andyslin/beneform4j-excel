package com.forms.beneform4j.excel.exports;

import java.io.OutputStream;

import com.forms.beneform4j.excel.data.loader.IDataLoader;
import com.forms.beneform4j.excel.model.base.IEM;

public interface IExcelExporter {

    public void export(IEM model, Object data, OutputStream output);

    public void export(IEM model, Object data, String filename);

<<<<<<< HEAD
    public void export(IEM model, Object param, Object data, OutputStream output);

    public void export(IEM model, Object param, Object data, String filename);

    public void export(IEM model, Object param, IDataLoader loader, OutputStream output);

    public void export(IEM model, Object param, IDataLoader loader, String filename);
=======
    public void export(IEM model, IDataLoader loader, Object param, OutputStream output);

    public void export(IEM model, IDataLoader loader, Object param, String filename);
>>>>>>> branch 'master' of http://192.168.22.190:8090/beneform4j/beneform4j-excel.git
}
