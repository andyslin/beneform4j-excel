package com.forms.beneform4j.excel.core.exports.base;

import java.io.OutputStream;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.data.loader.IDataLoader;
import com.forms.beneform4j.excel.core.exports.IExcelExporter;
import com.forms.beneform4j.excel.core.exports.file.FileEMExcelExporter;
import com.forms.beneform4j.excel.core.exports.tree.TreeEMExcelExporter;
import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.em.dynamic.IDynamicTreeEM;
import com.forms.beneform4j.excel.core.model.em.file.IFileEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEM;

public class BaseExcelExporter implements IExcelExporter {

    private static final IExcelExporter defaultTreeExporter = new TreeEMExcelExporter();

    private static final IExcelExporter defaultFileExporter = new FileEMExcelExporter();

    private IExcelExporter treeExporter = defaultTreeExporter;

    private IExcelExporter fileExporter = defaultFileExporter;

    /**
     * 注入树型模板的导出器
     * 
     * @param treeExporter
     */
    public void setTreeExporter(IExcelExporter treeExporter) {
        this.treeExporter = treeExporter;
    }

    /**
     * 注入文件模板导出器
     * 
     * @param fileExporter
     */
    public void setFileExporter(IExcelExporter fileExporter) {
        this.fileExporter = fileExporter;
    }

    @Override
    public void export(IEM model, Object data, OutputStream output) {
        IExcelExporter exporter = getExporter(model);
        exporter.export(model, data, output);
    }

    @Override
    public String export(IEM model, Object data, String filename) {
        IExcelExporter exporter = getExporter(model);
        return exporter.export(model, data, filename);
    }

    @Override
    public void export(IEM model, Object param, Object data, OutputStream output) {
        IExcelExporter exporter = getExporter(model);
        exporter.export(model, data, data, output);
    }

    @Override
    public String export(IEM model, Object param, Object data, String filename) {
        IExcelExporter exporter = getExporter(model);
        return exporter.export(model, param, data, filename);
    }

    @Override
    public void export(IEM model, Object param, IDataLoader loader, OutputStream output) {
        IExcelExporter exporter = getExporter(model);
        exporter.export(model, loader, param, output);
    }

    @Override
    public String export(IEM model, Object param, IDataLoader loader, String filename) {
        IExcelExporter exporter = getExporter(model);
        return exporter.export(model, loader, param, filename);
    }

    protected IExcelExporter getExporter(IEM model) {
        if (null == model) {
            throw Throw.createRuntimeException("Excel模型为空");
        } else if (model instanceof ITreeEM || model instanceof IDynamicTreeEM) {
            return treeExporter;
        } else if (model instanceof IFileEM) {
            return fileExporter;
        }
        throw Throw.createRuntimeException("不支持的Excel模型");
    }
}
