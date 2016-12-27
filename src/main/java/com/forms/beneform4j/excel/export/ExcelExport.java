package com.forms.beneform4j.excel.export;

import org.apache.poi.ss.usermodel.Workbook;

import com.forms.beneform4j.excel.export.builder.ModelExcelBuilder;
import com.forms.beneform4j.excel.export.builder.ModelExcelBuilderParam;
import com.forms.beneform4j.excel.export.datastream.wrap.IDataStreamHandlerWrap;
import com.forms.beneform4j.excel.export.grid.Grid;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Excel生成的工厂类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2015-5-8<br>
 */
public class ExcelExport {

    /**
     * 根据模型和数据处理器生成Excel对象
     * 
     * @param grid Excel模型
     * @param wrap 大数据量处理器包装对象
     * @param title 标题
     * @return
     */
    public static Workbook build(Grid grid, IDataStreamHandlerWrap wrap, String title) {
        return build(grid, wrap, title, false, true, true);
    }

    /**
     * 生成Excel对象
     * 
     * @param grid Excel模型
     * @param wrap 大数据量处理器包装对象
     * @param title 标题
     * @param writeTitle 是否在Excel文件中写标题
     * @param writePivotTable 是否生成数据透视表
     * @param autoFilter 是否设置自动筛选
     * @return
     */
    public static Workbook build(Grid grid, IDataStreamHandlerWrap wrap, String title, boolean writeTitle, boolean writePivotTable, boolean autoFilter) {
        ModelExcelBuilderParam param = new ModelExcelBuilderParam().setGrid(grid).setWrap(wrap).setTitle(title).setWriteTitle(writeTitle).setWritePivotTable(writePivotTable).setAutoFilter(autoFilter);
        return build(param);
    }

    /**
     * 根据参数生成Excel对象
     * 
     * @param param 构建参数
     * @return
     */
    public static Workbook build(ModelExcelBuilderParam param) {
        param.validate();
        ModelExcelBuilder builder = new ModelExcelBuilder();
        return builder.build(param);
    }
}
