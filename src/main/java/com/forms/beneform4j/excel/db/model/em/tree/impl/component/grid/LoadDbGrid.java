package com.forms.beneform4j.excel.db.model.em.tree.impl.component.grid;

import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Grid;

public class LoadDbGrid extends Grid {

    /**
     * 
     */
    private static final long serialVersionUID = -2228891998403559942L;

    /**
     * 加载的中间表
     */
    private String loadMidTable;

    /**
     * 加载后执行的SQL脚本
     */
    private String sqlScript;

    public String getLoadMidTable() {
        return loadMidTable;
    }

    public void setLoadMidTable(String loadMidTable) {
        this.loadMidTable = loadMidTable;
    }

    public String getSqlScript() {
        return sqlScript;
    }

    public void setSqlScript(String sqlScript) {
        this.sqlScript = sqlScript;
    }
}
