package com.forms.beneform4j.excel.core.exports;

import java.io.OutputStream;

import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.core.model.em.IEM;

public class ExcelExporters {

    public static void exports(String modelId, Object data, OutputStream output) {
        IExcelExporter exporter = getExporter();
        exporter.exports(modelId, data, output);
    }

    public static String exports(String modelId, Object data, String filename) {
        IExcelExporter exporter = getExporter();
        return exporter.exports(modelId, data, filename);
    }

    public static void exports(String modelId, Object param, Object data, OutputStream output) {
        IExcelExporter exporter = getExporter();
        exporter.exports(modelId, data, data, output);
    }

    public static String exports(String modelId, Object param, Object data, String filename) {
        IExcelExporter exporter = getExporter();
        return exporter.exports(modelId, param, data, filename);
    }

    public static void exports(IEM model, Object data, OutputStream output) {
        IExcelExporter exporter = getExporter();
        exporter.exports(model, data, output);
    }

    public static String exports(IEM model, Object data, String filename) {
        IExcelExporter exporter = getExporter();
        return exporter.exports(model, data, filename);
    }

    public static void exports(IEM model, Object param, Object data, OutputStream output) {
        IExcelExporter exporter = getExporter();
        exporter.exports(model, data, data, output);
    }

    public static String exports(IEM model, Object param, Object data, String filename) {
        IExcelExporter exporter = getExporter();
        return exporter.exports(model, param, data, filename);
    }

    private static IExcelExporter getExporter() {
        return ExcelComponentConfig.getExcelExporter();
    }
}
