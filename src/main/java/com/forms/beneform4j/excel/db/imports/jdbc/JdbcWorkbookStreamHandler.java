package com.forms.beneform4j.excel.db.imports.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.forms.beneform4j.excel.db.imports.AbstractDbWorkbookStreamHandler;
import com.forms.beneform4j.excel.db.model.ds.DataSourceConfig;
import com.forms.beneform4j.excel.db.model.em.tree.impl.component.grid.LoadDbGrid;
import com.forms.beneform4j.excel.db.model.em.tree.impl.component.grid.LoadDbTd;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 使用JDBC导入数据的回调处理器（通用）<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-3-9<br>
 */
public class JdbcWorkbookStreamHandler extends AbstractDbWorkbookStreamHandler {

    private static final int BATCH_SIZE = 5000;

    private final String sql;

    public JdbcWorkbookStreamHandler(LoadDbGrid grid) {
        super(grid);
        this.sql = initSql(grid);
    }

    /**
     * 初始化SQL语句
     * 
     * @param grid
     * @return
     */
    private String initSql(LoadDbGrid grid) {
        StringBuffer l = new StringBuffer();
        StringBuffer r = new StringBuffer();
        Map<Integer, LoadDbTd> loadTds = super.getLoadTds();
        for (LoadDbTd td : loadTds.values()) {
            l.append(",").append(td.getColumnName());
            r.append(",?");
        }
        StringBuffer sql = new StringBuffer()//
                .append("INSERT INTO ").append(grid.getTable())//
                .append("(").append(l.substring(1)).append(")")//
                .append("VALUES(").append(r.substring(1)).append(")");
        return sql.toString();
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    protected boolean handleRow(HandlerStatus status, List<String> cells, int insertColumns) {
        resolveValidRowData(cells, insertColumns);
        addBatch(status, cells);
        return true;//不用调用父类方法
    }

    private void addBatch(HandlerStatus status, List<String> cells) {
        try {
            Connection conn = null;
            PreparedStatement ps = null;
            String sql = null;

            ps = conn.prepareStatement(sql);
            ps.setString(1, cells.get(0));
            ps.setString(2, cells.get(1));

            ps.addBatch();

            if (status.getDataIndex() % BATCH_SIZE == 0) {//提交
                ps.executeBatch();
            }
        } catch (SQLException e) {

        }

    }

    @Override
    protected void doLoad(LoadDbGrid grid, DataSourceConfig dataSource, String filename) {
        // TODO Auto-generated method stub

    }

    //    private class JdbcBatchContext {
    //        private String sql;
    //    }
}
