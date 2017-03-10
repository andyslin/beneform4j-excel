package com.forms.beneform4j.excel.db.model.loader.xml.decorator;

import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Td;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.ITreeEMTdDecorator;
import com.forms.beneform4j.excel.db.model.em.tree.impl.component.grid.LoadDbTd;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Td装饰器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-3-7<br>
 */
public class LoadTreeEMTdDecorator implements ITreeEMTdDecorator {

    @Override
    public void decorate(Td td, Element tdElement, Element decorator) {
        if (!(td instanceof LoadDbTd)) {
            return;
        }

        LoadDbTd ltd = (LoadDbTd) td;
        setLoadDbTdProperties(ltd, decorator);
    }

    protected void setLoadDbTdProperties(LoadDbTd ltd, Element decorator) {
        String columnName = decorator.getAttribute("columnName");
        if (!CoreUtils.isBlank(columnName)) {
            ltd.setColumnName(columnName);//名称
            if (CoreUtils.isBlank(ltd.getExpression())) {
                ltd.setExpression(CoreUtils.convertToCamel(columnName));
            }
        }

        String convert = decorator.getAttribute("convert");
        if (!CoreUtils.isBlank(convert)) {
            ltd.setConvert(convert);
        }
    }
}
