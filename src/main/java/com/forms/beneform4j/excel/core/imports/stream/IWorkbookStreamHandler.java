package com.forms.beneform4j.excel.core.imports.stream;

import java.util.List;

public interface IWorkbookStreamHandler {

    /**
     * 初始化
     */
    public void initialize();

    /**
     * 开始解析一个Sheet表单
     * 
     * @param sheetIndex
     * @param sheetName
     * @return
     */
    public boolean startSheet(int sheetIndex, String sheetName);

    /**
     * 处理一行数据
     * 
     * @param cells
     * @param rowIndex
     * @return
     */
    public boolean row(List<String> cells, int rowIndex);

    /**
     * 结束一个表单解析
     * 
     * @param sheetIndex
     * @param sheetName
     * @return
     */
    public boolean endSheet(int sheetIndex, String sheetName);

    /**
     * 结束解析
     */
    public void end();

    /**
     * 默认单元格的值
     * 
     * @return
     */
    public String getDefaultCellValue();
}
