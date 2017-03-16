package com.forms.beneform4j.excel.db.imports;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.logger.CommonLogger;
import com.forms.beneform4j.excel.core.imports.stream.impl.TreeEMWorkbookStreamHandler;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Td;
import com.forms.beneform4j.excel.db.model.ds.DataSourceConfig;
import com.forms.beneform4j.excel.db.model.ds.DataSourceManager;
import com.forms.beneform4j.excel.db.model.em.tree.impl.component.grid.LoadDbGrid;
import com.forms.beneform4j.excel.db.model.em.tree.impl.component.grid.LoadDbTd;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 抽象的导入Excel数据至DB的回调处理器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-3-9<br>
 */
public abstract class AbstractDbWorkbookStreamHandler extends TreeEMWorkbookStreamHandler {

    private LoadDbGrid grid;

    private final DataSourceConfig dataSource;

    private final Map<Integer, LoadDbTd> loadTds = new LinkedHashMap<Integer, LoadDbTd>();

    public AbstractDbWorkbookStreamHandler(LoadDbGrid grid) {
        super(grid.getWorkbook());
        this.grid = grid;
        this.dataSource = DataSourceManager.getDataSource(grid.getDataSourceRef());
        super.setFilename(grid.getWorkbook().getId() + "-" + System.currentTimeMillis());
    }

    /**
     * 强制删除中间文件
     * 
     * @param filename
     */
    protected void forceDeleteFile(String... filename) {
        for (String f : filename) {
            try {
                File file = new File(f);
                if (file.exists() && !file.delete()) {
                    CommonLogger.warn("Unable to delete file: " + f);
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void initialize() {
        super.initialize();
        setLoadTds(loadTds, grid);
    }

    @Override
    protected boolean handleRow(HandlerStatus status, List<String> cells, int insertColumns) {
        resolveValidRowData(cells, insertColumns);
        return super.handleRow(status, cells, insertColumns);
    }

    /**
     * 解析有效的行数据
     * 
     * @param cells
     * @param insertColumns
     */
    protected void resolveValidRowData(List<String> cells, int insertColumns) {
        Set<Integer> set = loadTds.keySet();
        for (int i = cells.size() - 1; i >= insertColumns; i--) {
            if (!set.contains(i)) {
                cells.remove(i);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void end() {
        super.end();
        afterParse();
    }

    /**
     * 在解析执行完之后的处理
     */
    protected void afterParse() {
        try {
            DataSourceConfig dataSource = getDataSource();
            String filename = new File(this.getFilename()).getAbsolutePath();
            doLoad(grid, dataSource, filename);
            executeSqlScript(grid, dataSource);
        } finally {
            forceDeleteFile(getFilename());
        }
    }

    /**
     * 获取需要导入的有效配置
     * 
     * @return
     */
    protected Map<Integer, LoadDbTd> getLoadTds() {
        return loadTds;
    }

    /**
     * 获取模型配置
     * 
     * @return
     */
    protected LoadDbGrid getGrid() {
        return grid;
    }

    /**
     * 获取数据源配置
     * 
     * @return
     */
    protected DataSourceConfig getDataSource() {
        return dataSource;
    }

    /**
     * 执行导入操作
     * 
     * @param grid
     * @param dataSource
     * @param filename
     */
    protected abstract void doLoad(LoadDbGrid grid, DataSourceConfig dataSource, String filename);

    /**
     * 执行导入后的Sql脚本
     * 
     * @param grid
     * @param dataSource
     */
    protected void executeSqlScript(LoadDbGrid grid, DataSourceConfig dataSource) {
        String sqlScript = grid.getSqlScript();
        if (!CoreUtils.isBlank(sqlScript)) {//执行SQL脚本
            CommonLogger.debug(sqlScript);
        }
    }

    /**
     * 设置需要导入的有效配置
     * 
     * @param loadTds
     * @param grid
     */
    private void setLoadTds(Map<Integer, LoadDbTd> loadTds, LoadDbGrid grid) {
        int index = 0;
        if (isAddBatchNo()) {
            addColumn(index++, "BATCH_NO");
        }
        if (isAddDataIndex()) {
            addColumn(index++, "DATA_NO");
        }
        if (isAddRowIndex()) {
            addColumn(index++, "ROW_NO");
        }

        List<Td> leaf = grid.getLeaf();
        for (int i = 0, l = leaf.size(); i < l; i++) {
            LoadDbTd cell = (LoadDbTd) leaf.get(i);
            String column = cell.getColumnName();
            if (!CoreUtils.isBlank(column)) {
                loadTds.put(index + i, cell);
            }
        }
    }

    private void addColumn(int index, String columnName) {
        LoadDbTd td = new LoadDbTd();
        td.setColumnName(columnName);
        loadTds.put(index, td);
    }
}
