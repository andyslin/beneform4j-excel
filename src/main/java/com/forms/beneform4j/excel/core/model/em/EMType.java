package com.forms.beneform4j.excel.core.model.em;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Excel模型类别<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2016-12-22<br>
 */
public enum EMType {

    BEAN("bean", "导入-Bean模型"),

    TEXT("text", "导入-文本模型"),

    TREE("tree", "导入导出-树型结构模型"),

    FREEMARKER_TREE("freemarker-tree", "导出-使用Freemarker模板定义的树型结构模型"),

    EXCEL("excel", "导出-Excel模板文件"),

    JXLS2_EXCEL("jxls2-excel", "导出-jxls2类库使用的Excel模板文件，EXCEL类型的子类型"),

    JETT_EXCEL("jett-excel", "导出-jett类库使用的Excel模板文件，EXCEL类型的子类型"),

    UNKNOWN("", "未知类型");

    /**
     * 类别代码
     */
    private String code;

    /**
     * 类别描述
     */
    private String desc;

    /**
     * 构造函数
     * 
     * @param code
     * @param desc
     */
    private EMType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取描述
     * 
     * @return
     */
    public String getDesc() {
        return this.desc;
    }

    /**
     * 获取实例
     * 
     * @param code 类别代码
     * @return
     */
    public static EMType instance(String code) {
        for (EMType type : values()) {
            if (type.name().equalsIgnoreCase(code) || type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
