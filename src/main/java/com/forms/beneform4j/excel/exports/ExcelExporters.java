package com.forms.beneform4j.excel.exports;

import java.io.OutputStream;

import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.data.loader.IDataLoader;
import com.forms.beneform4j.excel.model.EMManager;
import com.forms.beneform4j.excel.model.base.IEM;

public class ExcelExporters {

    public static IEM loadExcelModel(String modelId) {
        return EMManager.load(modelId);
    }

    public static void export(IEM model, Object data, OutputStream output) {
        IExcelExporter exporter = getExporter(model);
        exporter.export(model, data, output);
    }

    public static void export(IEM model, Object data, String filename) {
        IExcelExporter exporter = getExporter(model);
        exporter.export(model, data, filename);
    }

    public static void export(IEM model, Object param, Object data, OutputStream output) {
        IExcelExporter exporter = getExporter(model);
        exporter.export(model, data, data, output);
    }

    public static void export(IEM model, Object param, Object data, String filename) {
        IExcelExporter exporter = getExporter(model);
        exporter.export(model, param, data, filename);
    }

    public static void export(IEM model, Object param, IDataLoader loader, OutputStream output) {
        IExcelExporter exporter = getExporter(model);
        exporter.export(model, loader, param, output);
    }

    public static void export(IEM model, Object param, IDataLoader loader, String filename) {
        IExcelExporter exporter = getExporter(model);
        exporter.export(model, loader, param, filename);
    }

    private static IExcelExporter getExporter(IEM model) {
        return ExcelComponentConfig.getExcelExporter();
    }
}
