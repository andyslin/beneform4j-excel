package com.forms.beneform4j.excel.db.imports.sqlldr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.env.EnvConsts;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.db.imports.AbstractDbWorkbookStreamHandler;
import com.forms.beneform4j.excel.db.model.ds.DataSourceConfig;
import com.forms.beneform4j.excel.db.model.em.tree.impl.component.grid.LoadDbGrid;
import com.forms.beneform4j.excel.db.model.em.tree.impl.component.grid.LoadDbTd;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 使用Sqlldr导入数据的回调处理器（用于Oracle数据库）<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-3-9<br>
 */
public class SqlldrWorkbookStreamHandler extends AbstractDbWorkbookStreamHandler {

    private final static String SPLIT = "X'1'";
    private final static String LINE_SPLIT = "X'170A'";

    public SqlldrWorkbookStreamHandler(LoadDbGrid grid) {
        super(grid);
    }

    @Override
    protected void doLoad(LoadDbGrid grid, DataSourceConfig dataSource, String filename) {
        try {
            this.sqlldrImport(grid, dataSource, filename);
        } catch (Exception e) {
            Throw.throwRuntimeException(e);
        }
    }

    private void sqlldrImport(LoadDbGrid grid, DataSourceConfig dataSource, String dataFile) throws Exception {
        String path = new File(dataFile).getParentFile().getPath();
        String batch = getStatus().getBatchNo();
        String ctlFile = path + "/" + batch + ".ctl";
        String logFile = path + "/" + batch + ".log";
        String cmdFile = path + "/" + batch + ".cmd";

        try {
            // 生成控制文件
            this.generateCtlFile(grid, dataFile, ctlFile);

            String shell = dataSource.getShell();
            if (CoreUtils.isBlank(shell)) {
                shell = cmdFile;
                String cmdfile0 = EnvConsts.IS_WINDOWS ? "import.cmd" : "import.sh";
                FileUtils.copyInputStreamToFile(SqlldrWorkbookStreamHandler.class.getResourceAsStream(cmdfile0), new File(shell));
            }

            List<String> commandList = new ArrayList<String>();
            if (EnvConsts.IS_WINDOWS) {
                commandList.add("cmd");
                commandList.add("/c");
            } else {
                CoreUtils.runOSCommand("chmod 755 " + path);
            }

            commandList.add(shell);
            commandList.add(dataSource.getUsername() + "/" + dataSource.getPassword() + "@" + dataSource.getTnsname());
            commandList.add(path);
            commandList.add(batch);
            CoreUtils.runOSCommand(commandList.toArray(new String[0]));
            String result = checkLogFile(logFile);
            if (!CoreUtils.isBlank(result)) {
                Throw.throwRuntimeException("执行SqlLoader命令失败【" + result + "】");
            }
        } finally {
            forceDeleteFile(ctlFile, logFile, cmdFile);
        }
    }

    private void generateCtlFile(LoadDbGrid grid, String dataFile, String ctlFile) throws IOException {
        PrintStream ps = null;
        try {
            ps = super.obtainPrintStream(ctlFile, true, getCharset());
            ps.println("load data");
            ps.println("infile '" + dataFile + "'" + " \"str " + LINE_SPLIT + "\"");
            ps.println(grid.getLoadType() + " into table " + grid.getTable() + "\n");

            String separator = super.getSeparator();
            if (CoreUtils.isBlank(separator)) {
                separator = SPLIT;
            } else {
                separator = "\"" + separator + "\"";
            }
            ps.println("fields terminated by " + separator + " (");

            List<String> columns = new ArrayList<String>();
            Map<Integer, LoadDbTd> loadTds = super.getLoadTds();
            for (LoadDbTd cell : loadTds.values()) {
                String column = cell.getColumnName();
                String fillExp = cell.getConvert();
                if (!CoreUtils.isBlank(fillExp)) {
                    columns.add(column + " \"" + fillExp + " \"");
                } else {
                    columns.add(column);
                }
            }

            for (int i = 0, l = columns.size(); i < l; i++) {
                ps.println(columns.get(i) + (i == l - 1 ? ")" : ","));
            }
        } finally {
            CoreUtils.closeQuietly(ps);
        }
    }

    private String checkLogFile(String logFilePath) throws Exception {
        String line = "";
        BufferedReader reader = null;
        try {
            File logFile = new File(logFilePath);
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(logFile)));
            while ((line = reader.readLine()) != null) {
                if (line.contains("ORA-")) {
                    return line;
                }
            }
            return null;
        } finally {
            CoreUtils.closeQuietly(reader);
        }
    }
}
