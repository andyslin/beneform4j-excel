package com.forms.beneform4j.excel.db.imports.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import com.forms.beneform4j.core.dao.util.DBHelp;
import com.forms.beneform4j.core.service.spring.SpringHelp;
import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;
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

    private DataSource ds;

    private Connection conn;

    private PreparedStatement ps;

    //    private boolean autoCommit;

    public JdbcWorkbookStreamHandler(LoadDbGrid grid) {
        super(grid);
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
        this.initializeConnection(getDataSource());
    }

    /**
     * 初始化数据库连接
     * 
     * @param dsConfig
     */
    protected void initializeConnection(DataSourceConfig dsConfig) {
        try {
            String dsBeanId = dsConfig.getBeanName();
            if (CoreUtils.isBlank(dsBeanId)) {
                ds = SpringHelp.getBean(DataSource.class);
            } else {
                ds = SpringHelp.getBean(dsBeanId, DataSource.class);
            }
            this.conn = DataSourceUtils.getConnection(ds);

            // 这里可以对导入方式做处理，如覆盖导入，可以先删除数据，
            // 也可以把导入方式放入到存储过程中处理

            String sql = initSql(super.getGrid());
            this.ps = conn.prepareStatement(sql);
            //            this.autoCommit = conn.getAutoCommit();//记住之前的提交
            //            //先提交一次
            //            if (!this.autoCommit) {
            //                this.conn.commit();
            //            }
            //            this.conn.setAutoCommit(false);
        } catch (SQLException e) {
            Throw.throwRuntimeException(e);
        }
    }

    @Override
    public void end() {
        try {
            ps.executeBatch();
            //            this.conn.setAutoCommit(autoCommit);//恢复之前的提交模式
        } catch (Exception e) {
            Throw.throwRuntimeException(e);
        } finally {
            DBHelp.Closer.close(ps);
            DataSourceUtils.releaseConnection(conn, ds);
            super.end();
        }
    }

    @Override
    protected boolean handleRow(HandlerStatus status, List<String> cells, int insertColumns) {
        resolveValidRowData(cells, insertColumns);
        addBatch(status, cells);
        return true;//不用调用父类方法
    }

    private void addBatch(HandlerStatus status, List<String> cells) {
        try {
            int i = 0;
            Map<Integer, LoadDbTd> tds = super.getLoadTds();
            for (LoadDbTd td : tds.values()) {
                setParam(ps, i + 1, cells.get(i), td);
                i++;
            }
            ps.addBatch();

            if (status.getDataIndex() % BATCH_SIZE == 0) {//提交
                ps.executeBatch();
            }
        } catch (SQLException e) {
            Throw.throwRuntimeException(e);
        }
    }

    private void setParam(PreparedStatement ps, int index, String value, LoadDbTd td) throws SQLException {
        switch (td.getDataTypeEnum()) {
            case CHAR:
                ps.setString(index, value);
                break;
            case VARCHAR:
                ps.setString(index, value);
                break;
            case INTEGER:
                ps.setInt(index, Integer.parseInt(value));
                break;
            case DECIMAL:
                ps.setDouble(index, Double.parseDouble(value));
                break;
            case PERCENTAG:
                if (value.endsWith("%")) {
                    ps.setDouble(index, Double.parseDouble(value.replace('%', ' ')) / 100.0);
                } else {
                    ps.setDouble(index, Double.parseDouble(value));
                }
                break;
            case UNKNOWN:
                ps.setObject(index, value);
                break;
        }
    }

    @Override
    protected void doLoad(LoadDbGrid grid, DataSourceConfig dataSource, String filename) {
        // empty

    }
}
