package com.forms.beneform4j.excel.db.model.loader.xml.tree.component;

import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Grid;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Td;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.component.GridParser;
import com.forms.beneform4j.excel.db.model.em.tree.impl.component.grid.LoadDbGrid;
import com.forms.beneform4j.excel.db.model.em.tree.impl.component.grid.LoadDbTd;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 上传至DB的Grid组件解析器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-3-7<br>
 */
public class LoadDbGridParser extends GridParser {

    @Override
    protected Grid newGrid() {
        return new LoadDbGrid();
    }

    @Override
    protected Td newTd() {
        return new LoadDbTd();
    }
}
