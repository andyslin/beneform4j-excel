package com.forms.beneform4j.excel.exports;

import java.io.OutputStream;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.data.loader.IDataLoader;
import com.forms.beneform4j.excel.exports.file.FileEMExcelExporter;
import com.forms.beneform4j.excel.exports.tree.TreeEMExcelExporter;
import com.forms.beneform4j.excel.model.base.IEM;
import com.forms.beneform4j.excel.model.dynamic.IDynamicTreeEM;
import com.forms.beneform4j.excel.model.file.IFileEM;
import com.forms.beneform4j.excel.model.tree.ITreeEM;

public class BaseExcelExporter implements IExcelExporter {

    private final IExcelExporter tree = new TreeEMExcelExporter();

    private final IExcelExporter file = new FileEMExcelExporter();

    @Override
    public void export(IEM model, Object data, OutputStream output) {
        IExcelExporter exporter = getExporter(model);
        exporter.export(model, data, output);
    }

    @Override
    public void export(IEM model, Object data, String filename) {
        IExcelExporter exporter = getExporter(model);
        exporter.export(model, data, filename);
    }

    @Override
    public void export(IEM model, IDataLoader loader, Object param, OutputStream output) {
        IExcelExporter exporter = getExporter(model);
        exporter.export(model, loader, param, output);
    }

    @Override
    public void export(IEM model, IDataLoader loader, Object param, String filename) {
        IExcelExporter exporter = getExporter(model);
        exporter.export(model, loader, param, filename);
    }

    protected IExcelExporter getExporter(IEM model) {
        if (model instanceof ITreeEM) {
            return tree;
        } else if (model instanceof IDynamicTreeEM) {
            return tree;
        } else if (model instanceof IFileEM) {
            return file;
        } else {
            throw Throw.createRuntimeException("不支持的Excel模型");
        }
    }
}
