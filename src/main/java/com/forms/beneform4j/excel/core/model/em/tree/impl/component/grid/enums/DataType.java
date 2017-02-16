package com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.enums;

import java.sql.Types;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 表格组件列使用的数据类型<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public enum DataType {
    INTEGER("0", "right", Types.INTEGER), // 整型
    DECIMAL("#,##0.00", "right", Types.DOUBLE), // 数字类型
    PERCENTAG("0.00%", "right", Types.DOUBLE), // 百分数
    CHAR("@", "center", Types.CHAR), // 定长文本
    VARCHAR("@", "left", Types.VARCHAR), // 变长文本
    // DATE("yyyyMMdd","center", Types.DATE), // 日期类型
    UNKNOWN("@", "left", Types.VARCHAR); // 其它未知类型

    private String dataFormat; // 数据格式
    private String alignType; // 对齐方式
    private int sqlType; // SQL类型

    /**
     * 私有化构造函数
     * 
     * @param dataFormat
     * @param alignType
     */
    private DataType(String dataFormat, String alignType, int sqlType) {
        this.dataFormat = dataFormat;
        this.alignType = alignType;
        this.sqlType = sqlType;
    }

    /**
     * 返回实例
     * 
     * @param dataType
     * @return
     */
    public static DataType instance(String dataType) {
        if (null == dataType) {
            return UNKNOWN;
        }
        String t = dataType.toUpperCase().trim();
        if (-1 != t.indexOf("INT")) {
            return INTEGER;
        } else if (-1 != t.indexOf("NUM") || -1 != t.indexOf("DEC") || -1 != t.indexOf("DOU")) {
            return DECIMAL;
        }
        for (DataType type : DataType.values()) {
            if (type.name().equals(t)) {
                return type;
            }
        }
        return UNKNOWN;
    }

    /**
     * 获取默认数据格式
     * 
     * @return
     */
    public String getDataFormat() {
        return dataFormat;
    }

    /**
     * 获取默认对齐方式
     * 
     * @return
     */
    public String getAlignType() {
        return alignType;
    }

    /**
     * 获取SQL类型
     * 
     * @return
     */
    public int getSqlType() {
        return sqlType;
    }
}
