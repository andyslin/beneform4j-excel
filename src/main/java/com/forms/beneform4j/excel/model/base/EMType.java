package com.forms.beneform4j.excel.model.base;

import org.apache.commons.io.FilenameUtils;

public class EMType {

    public static final String MODEL_TYPE_XML_TREE = "xml-tree";
    public static final String MODEL_TYPE_EXCEL = "excel";
    public static final String MODEL_TYPE_FREEMARKER = "freemarker";

    public static String getTypeBySuffix(String suffix) {
        if ("xls".equalsIgnoreCase(suffix) || "xlsx".equalsIgnoreCase(suffix)) {
            return MODEL_TYPE_EXCEL;
        } else {
            return MODEL_TYPE_FREEMARKER;
        }
    }

    public static String getTypeByFilename(String filename) {
        String suffix = FilenameUtils.getExtension(filename);
        return getTypeBySuffix(suffix);
    }
}
