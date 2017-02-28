package com.forms.beneform4j.excel.db.model.em.tree.impl.component.grid;

import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Td;

public class LoadDbTd extends Td {

    /**
     * 
     */
    private static final long serialVersionUID = -7010989934156714219L;

    /**
     * 列名
     */
    private String columnName;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

}
