package com.forms.beneform4j.excel.db.imports.bcp;

import java.io.File;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.core.util.logger.CommonLogger;
import com.forms.beneform4j.excel.db.imports.AbstractDbWorkbookStreamHandler;
import com.forms.beneform4j.excel.db.model.ds.DataSourceConfig;
import com.forms.beneform4j.excel.db.model.em.tree.impl.component.grid.LoadDbGrid;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 使用bcp导入数据的回调处理器（用于Sybase数据库）<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-3-9<br>
 */
public class BcpWorkbookStreamHandler extends AbstractDbWorkbookStreamHandler {

    public BcpWorkbookStreamHandler(LoadDbGrid grid) {
        super(grid);
    }

    @Override
    protected void doLoad(LoadDbGrid grid, DataSourceConfig dataSource, String filename) {
        this.loadData(grid, dataSource, filename, getSeparator());
    }

    private void loadData(LoadDbGrid grid, DataSourceConfig dataSource, String filename, String separator) {
        String tablename = grid.getTable();
        try {
            if (new File(filename).length() == 0) {
                CommonLogger.info(filename + " 为空，不执行bcp命令，直接返回");
                return;
            }
            String serverName = dataSource.getTnsname();
            String user = dataSource.getUsername();
            String password = dataSource.getPassword();

            String type = " in ";
            String cmd = "bcp " + tablename + type + "\"" + filename + "\" -S" + serverName + " -U" + user + " -P" + password + " -b500  -Jcp936 -t\"" + separator + "\" -c ";
            CommonLogger.info("执行bcp命令:" + cmd);
            CoreUtils.runOSCommand(cmd);;
        } catch (Exception e) {
            Throw.throwRuntimeException("执行bcp命令出现异常，表名：" + tablename + "，文件名:" + filename + "，请检查...", e);
        }
    }
}
