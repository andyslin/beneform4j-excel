package com.forms.beneform4j.excel.core.exports;

import java.io.OutputStream;

import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.core.model.em.EMManager;
import com.forms.beneform4j.excel.core.model.em.IEM;

public class ExcelExporters {

    public static void exports(String modelId, Object data, OutputStream output) {
        IEM model = getModel(modelId);
        IExcelExporter exporter = getExporter(model);
        exporter.exports(model, data, output);
    }

    public static String exports(String modelId, Object data, String filename) {
        IEM model = getModel(modelId);
        IExcelExporter exporter = getExporter(model);
        return exporter.exports(model, data, filename);
    }

    public static void exports(String modelId, Object param, Object data, OutputStream output) {
        IEM model = getModel(modelId);
        IExcelExporter exporter = getExporter(model);
        exporter.exports(model, data, data, output);
    }

    public static String exports(String modelId, Object param, Object data, String filename) {
        IEM model = getModel(modelId);
        IExcelExporter exporter = getExporter(model);
        return exporter.exports(model, param, data, filename);
    }

    public static void exports(IEM model, Object data, OutputStream output) {
        IExcelExporter exporter = getExporter(model);
        exporter.exports(model, data, output);
    }

    public static String exports(IEM model, Object data, String filename) {
        IExcelExporter exporter = getExporter(model);
        return exporter.exports(model, data, filename);
    }

    public static void exports(IEM model, Object param, Object data, OutputStream output) {
        IExcelExporter exporter = getExporter(model);
        exporter.exports(model, data, data, output);
    }

    public static String exports(IEM model, Object param, Object data, String filename) {
        IExcelExporter exporter = getExporter(model);
        return exporter.exports(model, param, data, filename);
    }

    private static IEM getModel(String modelId) {
        return EMManager.load(modelId);
    }

    private static IExcelExporter getExporter(IEM model) {
        return ExcelComponentConfig.getExcelExporter();
    }
}
