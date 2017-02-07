package com.forms.beneform4j.excel.core.imports;

import java.io.InputStream;

import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.core.imports.stream.IWorkbookStreamHandler;
import com.forms.beneform4j.excel.core.model.em.IEM;

public class ExcelImporters {

    public static void imports(InputStream input, IWorkbookStreamHandler handler) {
        IExcelImporter importer = getImporter();
        importer.imports(input, handler);
    }

    public static void imports(String location, IWorkbookStreamHandler handler) {
        IExcelImporter importer = getImporter();
        importer.imports(location, handler);
    }

    public static <T> T imports(InputStream input, Class<T> cls) {
        IExcelImporter importer = getImporter();
        return importer.imports(input, cls);
    }

    public static <T> T imports(String location, Class<T> cls) {
        IExcelImporter importer = getImporter();
        return importer.imports(location, cls);
    }

    public static Object imports(InputStream input, String modelId) {
        IExcelImporter importer = getImporter();
        return importer.imports(input, modelId);
    }

    public static Object imports(String location, String modelId) {
        IExcelImporter importer = getImporter();
        return importer.imports(location, modelId);
    }

    public static <T> T imports(InputStream input, String modelId, Class<T> cls) {
        IExcelImporter importer = getImporter();
        return importer.imports(input, modelId, cls);
    }

    public static <T> T imports(String location, String modelId, Class<T> cls) {
        IExcelImporter importer = getImporter();
        return importer.imports(location, modelId, cls);
    }

    public static Object imports(InputStream input, IEM model) {
        IExcelImporter importer = getImporter();
        return importer.imports(input, model);
    }

    public static Object imports(String location, IEM model) {
        IExcelImporter importer = getImporter();
        return importer.imports(location, model);
    }

    private static IExcelImporter getImporter() {
        return ExcelComponentConfig.getExcelImporter();
    }
}
