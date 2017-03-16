package com.forms.beneform4j.excel.db.imports;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.imports.ExcelImporters;
import com.forms.beneform4j.excel.core.imports.stream.IWorkbookStreamHandler;
import com.forms.beneform4j.excel.core.model.em.EMManager;
import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.db.imports.bcp.BcpWorkbookStreamHandler;
import com.forms.beneform4j.excel.db.imports.jdbc.JdbcWorkbookStreamHandler;
import com.forms.beneform4j.excel.db.imports.sqlldr.SqlldrWorkbookStreamHandler;
import com.forms.beneform4j.excel.db.model.ds.DataSourceConfig;
import com.forms.beneform4j.excel.db.model.ds.DataSourceManager;
import com.forms.beneform4j.excel.db.model.em.tree.impl.component.grid.LoadDbGrid;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Excel-DB模块-导入数据<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-3-9<br>
 */
public class DbExcelImporters {

    private static final Map<String, Class<? extends AbstractDbWorkbookStreamHandler>> handlers = new HashMap<String, Class<? extends AbstractDbWorkbookStreamHandler>>();
    private static final Class<? extends AbstractDbWorkbookStreamHandler> defaultHandler = JdbcWorkbookStreamHandler.class;
    static {
        handlers.put("oracle", SqlldrWorkbookStreamHandler.class);
        handlers.put("sybase ase", BcpWorkbookStreamHandler.class);
    }

    /**
     * 加载数据至数据库
     * 
     * @param input 表示Excel文件的输入流
     * @param modelId 模型ID
     */
    public static void load(InputStream input, String modelId) {
        IWorkbookStreamHandler handler = getWorkbookStreamHandler(modelId);
        ExcelImporters.imports(input, handler);
    }

    /**
     * 加载数据至数据库
     * 
     * @param location 表示Excel文件的路径
     * @param modelId 模型ID
     */
    public static void load(String location, String modelId) {
        IWorkbookStreamHandler handler = getWorkbookStreamHandler(modelId);
        ExcelImporters.imports(location, handler);
    }

    private static IWorkbookStreamHandler getWorkbookStreamHandler(String modelId) {
        LoadDbGrid grid = getLoadDbGrid(modelId);
        DataSourceConfig dataSource = DataSourceManager.getDataSource(grid.getDataSourceRef());
        if (null == dataSource) {
            Throw.throwRuntimeException("未找到目标数据源");
        }

        return newWorkbookStreamHandler(grid, dataSource);
    }

    private static IWorkbookStreamHandler newWorkbookStreamHandler(LoadDbGrid grid, DataSourceConfig dataSource) {
        Class<? extends AbstractDbWorkbookStreamHandler> cls = defaultHandler;
        if (!dataSource.isUseJdbc()) {
            String database = dataSource.getDatabase();
            if (!CoreUtils.isBlank(database)) {
                cls = handlers.get(database.toLowerCase());
            }
            if (null == cls) {
                cls = defaultHandler;
            }
        }

        try {
            Constructor<? extends AbstractDbWorkbookStreamHandler> constructor = cls.getConstructor(LoadDbGrid.class);
            return constructor.newInstance(grid);
        } catch (Exception e) {
            throw Throw.createRuntimeException(e);
        }
    }

    private static LoadDbGrid getLoadDbGrid(String modelId) {
        IEM em = null;
        if (!CoreUtils.isBlank(modelId)) {
            em = EMManager.load(modelId);
        }

        if (null == em) {
            Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS07);
        } else if (em instanceof ITreeEM) {
            ITreeEM model = (ITreeEM) em;
            ITreeEMComponent component = model.getFirstComponent();
            if (!(component instanceof LoadDbGrid)) {
                Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS32, model);
            }
            return (LoadDbGrid) component;
        } else {
            Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS08, em);
        }
        return null;
    }
}
