package com.forms.beneform4j.excel.db.model.loader.xml.tree.component;

import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Grid;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Td;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.component.GridParser;
import com.forms.beneform4j.excel.db.model.em.tree.impl.component.grid.LoadDbGrid;
import com.forms.beneform4j.excel.db.model.em.tree.impl.component.grid.LoadDbTd;

public class LoadDbGridParser extends GridParser {

    @Override
    protected Grid newGrid() {
        return new LoadDbGrid();
    }

    @Override
    protected Td newTd() {
        return new LoadDbTd();
    }

    @Override
    protected void setTdProperties(String modelId, Element ele, Td td) {
        super.setTdProperties(modelId, ele, td);
        LoadDbTd ltd = (LoadDbTd) td;

        String columnName = ele.getAttribute("columnName");
        if (!CoreUtils.isBlank(columnName)) {
            ltd.setColumnName(columnName);//名称
            if (CoreUtils.isBlank(td.getExpression())) {
                td.setExpression(CoreUtils.convertToCamel(columnName));
            }
        }
    }
}
