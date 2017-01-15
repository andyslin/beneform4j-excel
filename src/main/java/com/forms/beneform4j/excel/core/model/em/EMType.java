package com.forms.beneform4j.excel.core.model.em;

import org.apache.commons.io.FilenameUtils;

public enum EMType {

    TREE("tree", "树型结构模型"),

    FREEMARKER_TREE("freemarker-tree", "使用Freemarker模板定义的属性结构模型"),

    EXCEL("excel", "Excel模板文件"),

    JXLS2_EXCEL("jxls2-excel", "jxls2类库使用的Excel模板文件，EXCEL类型的子类型"),

    JETT_EXCEL("jett-excel", "jett类库使用的Excel模板文件，EXCEL类型的子类型"),

    UNKNOWN("", "未知类型");

    private String code;

    private String desc;

    private EMType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

    public static EMType instance(String code) {
        for (EMType type : values()) {
            if (type.name().equalsIgnoreCase(code) || type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        return UNKNOWN;
    }

    public static EMType getTypeBySuffix(String suffix) {
        if ("xls".equalsIgnoreCase(suffix) || "xlsx".equalsIgnoreCase(suffix)) {
            return EXCEL;
        } else {
            return FREEMARKER_TREE;
        }
    }

    public static EMType getTypeByFilename(String filename) {
        String suffix = FilenameUtils.getExtension(filename);
        return getTypeBySuffix(suffix);
    }
}
