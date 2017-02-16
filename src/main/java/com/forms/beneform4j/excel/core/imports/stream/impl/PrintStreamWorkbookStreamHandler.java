package com.forms.beneform4j.excel.core.imports.stream.impl;

import java.io.PrintStream;
import java.util.List;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 逐行处理的向PrintStream输出的回调处理器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class PrintStreamWorkbookStreamHandler extends WorkbookStreamHandlerSupport {

    private PrintStream printStream;

    private String separator;

    @Override
    public void initialize() {
        PrintStream ps = getPrintStream();
        if (null == ps) {
            Throw.throwRuntimeException("PrintStream is null.");
        }

        String separator = getSeparator();
        if (CoreUtils.isBlank(separator)) {
            setSeparator(",");
        }
        super.initialize();
    }

    @Override
    protected boolean handleRow(HandlerStatus status, List<String> cells, int rowIndex, boolean isEmptyRow) {
        PrintStream ps = getPrintStream();
        String spearator = getSeparator();
        this.beforeHandleRow(ps, spearator, cells, rowIndex);
        if (null != cells && !cells.isEmpty()) {
            ps.print(cells.get(0));
            for (int i = 1, l = cells.size(); i < l; i++) {
                ps.print(spearator);
                ps.print(cells.get(i));
            }
        }
        this.afterHandleRow(ps, spearator, cells, rowIndex);
        ps.println();
        return super.handleRow(status, cells, rowIndex, isEmptyRow);
    }

    protected void beforeHandleRow(PrintStream printStream, String spearator, List<String> cells, int rowIndex) {

    }

    protected void afterHandleRow(PrintStream printStream, String spearator, List<String> cells, int rowIndex) {

    }

    public PrintStream getPrintStream() {
        return printStream;
    }

    public void setPrintStream(PrintStream printStream) {
        this.printStream = printStream;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
