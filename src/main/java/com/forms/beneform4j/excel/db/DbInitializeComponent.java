package com.forms.beneform4j.excel.db;

import com.forms.beneform4j.excel.InitializeComponent;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoaderConfig;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoaderConsts;
import com.forms.beneform4j.excel.db.model.loader.xml.decorator.LoadTreeEMDecorator;
import com.forms.beneform4j.excel.db.model.loader.xml.decorator.LoadTreeEMTdDecorator;
import com.forms.beneform4j.excel.db.model.loader.xml.ds.DsTopElementParser;
import com.forms.beneform4j.excel.db.model.loader.xml.tree.component.LoadDbGridParser;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Excel-DB模块初始化<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-3-7<br>
 */
public class DbInitializeComponent implements InitializeComponent {

    /**
     * 命名空间
     */
    public static final String DB_NAMESPACE = "http://www.formssi.com/schema/beneform4j/excel-db";

    @Override
    public void initialize() {
        XmlEMLoaderConfig.registerTopElementParser(DB_NAMESPACE, "data-source", new DsTopElementParser());
        XmlEMLoaderConfig.registerTreeComponentParser(XmlEMLoaderConsts.GRID_COMPONENT_TYPE, new LoadDbGridParser());
        XmlEMLoaderConfig.registerTreeEMDecorator(DB_NAMESPACE, "load", new LoadTreeEMDecorator());
        XmlEMLoaderConfig.registerTreeEMTdDecorator(DB_NAMESPACE, "load-td", new LoadTreeEMTdDecorator());
    }

}
