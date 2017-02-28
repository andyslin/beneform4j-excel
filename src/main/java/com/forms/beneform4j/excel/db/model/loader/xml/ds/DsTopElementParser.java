package com.forms.beneform4j.excel.db.model.loader.xml.ds;

import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.core.model.loader.IResourceEMLoadContext;
import com.forms.beneform4j.excel.core.model.loader.xml.IEMTopElementParser;
import com.forms.beneform4j.excel.db.model.ds.DataSourceConfig;
import com.forms.beneform4j.excel.db.model.ds.DataSourceManager;

public class DsTopElementParser implements IEMTopElementParser {

    @Override
    public void parse(IResourceEMLoadContext context, Element element) {
        String id = element.getAttribute("id");
        DataSourceConfig ds = new DataSourceConfig();

        String username = element.getAttribute("username");
        if (!CoreUtils.isBlank(username)) {
            ds.setUsername(username);
        }

        String password = element.getAttribute("password");
        if (!CoreUtils.isBlank(password)) {
            ds.setPassword(password);
        }

        String tnsname = element.getAttribute("tnsname");
        if (!CoreUtils.isBlank(tnsname)) {
            ds.setTnsname(tnsname);
        }

        String encoding = element.getAttribute("encoding");
        if (!CoreUtils.isBlank(encoding)) {
            ds.setEncoding(encoding);
        }

        DataSourceManager.register(id, ds);
    }

}
