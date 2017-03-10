package com.forms.beneform4j.excel.db.model.em.tree.impl.component.grid;

import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Td;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 上传至DB的td对象<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-3-7<br>
 */
public class LoadDbTd extends Td {

    /**
     * 
     */
    private static final long serialVersionUID = -7010989934156714219L;

    /**
     * 列名
     */
    private String columnName;

    /**
     * 转换表达式
     */
    private String convert;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getConvert() {
        return convert;
    }

    public void setConvert(String convert) {
        this.convert = convert;
    }
}
