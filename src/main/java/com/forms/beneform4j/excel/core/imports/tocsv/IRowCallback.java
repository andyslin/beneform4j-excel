package com.forms.beneform4j.excel.core.imports.tocsv;

import java.util.List;

public interface IRowCallback {

    /**
     * 执行回调
     * 
     * @param cells 数据
     * @param sheetName sheet名称
     * @param sheetIndex sheet索引
     * @param index 行索引
     * @return 是否继续处理
     */
    public boolean callback(List<String> cells, String sheetName, int sheetIndex, int index);
}
