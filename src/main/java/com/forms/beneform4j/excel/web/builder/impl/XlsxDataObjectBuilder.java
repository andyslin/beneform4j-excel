package com.forms.beneform4j.excel.web.builder.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.forms.beneform4j.excel.export.ExcelExport;
import com.forms.beneform4j.excel.export.builder.ModelExcelBuilderParam;
import com.forms.beneform4j.excel.export.datastream.wrap.IDataStreamHandlerWrap;
import com.forms.beneform4j.excel.model.tree.component.grid.Grid;
import com.forms.beneform4j.excel.web.builder.IDataObjectBuilder;

public class XlsxDataObjectBuilder implements IDataObjectBuilder {

    @Override
    public Object buildDataObject(HttpServletRequest request, Grid grid, IDataStreamHandlerWrap wrap, Map<String, Object> model) {
        ModelExcelBuilderParam param = new ModelExcelBuilderParam().setGrid(grid).setWrap(wrap).setTitle(grid.getModelName())
        // .setTitle(title)
        // .setWriteTitle(writeTitle)
        // .setWritePivotTable(writePivotTable)
        // .setAutoFilter(autoFilter)
        ;

        return ExcelExport.build(param);
    }

}
