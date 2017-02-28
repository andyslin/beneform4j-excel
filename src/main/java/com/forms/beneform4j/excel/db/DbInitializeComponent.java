package com.forms.beneform4j.excel.db;

import com.forms.beneform4j.excel.InitializeComponent;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoaderConfig;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoaderConsts;
import com.forms.beneform4j.excel.db.model.loader.xml.ds.DsTopElementParser;
import com.forms.beneform4j.excel.db.model.loader.xml.tree.component.LoadDbGridParser;

public class DbInitializeComponent implements InitializeComponent {

    /**
     * 命名空间
     */
    public static final String DB_NAMESPACE = "http://www.formssi.com/schema/beneform4j/excel-db";

    @Override
    public void initialize() {
        XmlEMLoaderConfig.registerTopElementParser(DB_NAMESPACE, "data-source", new DsTopElementParser());

        XmlEMLoaderConfig.registerTreeComponentParser(XmlEMLoaderConsts.GRID_COMPONENT_TYPE, new LoadDbGridParser());

    }

}
