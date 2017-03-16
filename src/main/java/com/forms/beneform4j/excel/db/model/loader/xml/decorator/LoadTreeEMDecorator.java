package com.forms.beneform4j.excel.db.model.loader.xml.decorator;

import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.ITreeEMDecorator;
import com.forms.beneform4j.excel.db.model.em.tree.impl.component.grid.LoadDbGrid;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : TreeEM装饰器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-3-7<br>
 */
public class LoadTreeEMDecorator implements ITreeEMDecorator {

    @Override
    public void decorate(ITreeEM model, Element workbook, Element decorator) {
        ITreeEMComponent component = model.getFirstComponent();
        if (!(component instanceof LoadDbGrid)) {
            Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS32, model);
        }

        LoadDbGrid grid = (LoadDbGrid) component;
        this.setLoadDbProperties(grid, decorator);
    }

    protected void setLoadDbProperties(LoadDbGrid grid, Element decorator) {
        String dataSourceRef = decorator.getAttribute("dataSource-ref");
        if (!CoreUtils.isBlank(dataSourceRef)) {
            grid.setDataSourceRef(dataSourceRef);
        } /*
           * else { Throw.throwRuntimeException("未配置数据源引用"); }
           */

        String table = decorator.getAttribute("table");
        if (!CoreUtils.isBlank(table)) {
            grid.setTable(table);
        } else {
            Throw.throwRuntimeException("未配置表名");
        }

        String loadType = decorator.getAttribute("loadType");
        if (!CoreUtils.isBlank(loadType)) {
            grid.setLoadType(loadType);
        } else {
            grid.setLoadType("APPEND");
        }

        String sqlScript = decorator.getAttribute("sqlScript");
        if (!CoreUtils.isBlank(sqlScript)) {
            grid.setSqlScript(sqlScript);
        }
    }
}
