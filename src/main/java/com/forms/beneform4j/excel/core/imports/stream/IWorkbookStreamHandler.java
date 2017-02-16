package com.forms.beneform4j.excel.core.imports.stream;

import java.util.List;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Excel对象流式解析的回调处理器接口<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
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
     * 单元格的默认值
     * 
     * @return
     */
    public String getDefaultCellValue();
}
