package com.forms.beneform4j.excel.export.builder;

import com.forms.beneform4j.excel.export.datastream.wrap.IDataStreamHandlerWrap;
import com.forms.beneform4j.excel.export.grid.Grid;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Excel对象生成类的参数类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2015-5-8<br>
 */
public class ModelExcelBuilderParam {

    // Excel模型
    private Grid grid;

    // 流式数据处理器包装对象
    private IDataStreamHandlerWrap wrap;

    // 标题
    private String title;

    // 是否在Excel文件中写标题
    private boolean writeTitle = true;

    // 是否生成数据透视表
    private boolean writePivotTable = true;

    // 是否设置自动筛选
    private boolean autoFilter = true;

    public ModelExcelBuilderParam validate() {
        if (null == grid) {
            throw new RuntimeException("Excel构建模型不能为空");
        }
        if (null == wrap) {
            throw new RuntimeException("数据处理器不能为空");
        }
        return this;
    }

    public Grid getGrid() {
        return grid;
    }

    public ModelExcelBuilderParam setGrid(Grid grid) {
        this.grid = grid;
        return this;
    }

    public IDataStreamHandlerWrap getWrap() {
        return wrap;
    }

    public ModelExcelBuilderParam setWrap(IDataStreamHandlerWrap wrap) {
        this.wrap = wrap;
        return this;
    }

    public String getTitle() {
        return title == null ? grid.getModelName() : title;
    }

    public ModelExcelBuilderParam setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isWriteTitle() {
        return writeTitle;
    }

    public ModelExcelBuilderParam setWriteTitle(boolean writeTitle) {
        this.writeTitle = writeTitle;
        return this;
    }

    public boolean isWritePivotTable() {
        return writePivotTable;
    }

    public ModelExcelBuilderParam setWritePivotTable(boolean writePivotTable) {
        this.writePivotTable = writePivotTable;
        return this;
    }

    public boolean isAutoFilter() {
        return autoFilter;
    }

    public ModelExcelBuilderParam setAutoFilter(boolean autoFilter) {
        this.autoFilter = autoFilter;
        return this;
    }
}
