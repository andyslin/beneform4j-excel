package com.forms.beneform4j.excel.db.model.em.tree.impl.component.grid;

import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Grid;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 上传至DB的Grid组件<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-3-7<br>
 */
public class LoadDbGrid extends Grid {

    /**
     * 
     */
    private static final long serialVersionUID = -2228891998403559942L;

    /**
     * 数据源引用ID
     */
    private String dataSourceRef;

    /**
     * 加载方式
     */
    private String loadType;

    /**
     * 上传中间表名
     */
    private String table;

    /**
     * 加载后执行的SQL脚本
     */
    private String sqlScript;

    public String getDataSourceRef() {
        return dataSourceRef;
    }

    public void setDataSourceRef(String dataSourceRef) {
        this.dataSourceRef = dataSourceRef;
    }

    public String getLoadType() {
        return loadType;
    }

    public void setLoadType(String loadType) {
        this.loadType = loadType;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getSqlScript() {
        return sqlScript;
    }

    public void setSqlScript(String sqlScript) {
        this.sqlScript = sqlScript;
    }
}
