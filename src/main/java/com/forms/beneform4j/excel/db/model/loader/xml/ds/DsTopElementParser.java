package com.forms.beneform4j.excel.db.model.loader.xml.ds;

import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.core.model.loader.IResourceEMLoadContext;
import com.forms.beneform4j.excel.core.model.loader.xml.IEMTopElementParser;
import com.forms.beneform4j.excel.db.model.ds.DataSourceConfig;
import com.forms.beneform4j.excel.db.model.ds.DataSourceManager;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 数据源解析器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-3-7<br>
 */
public class DsTopElementParser implements IEMTopElementParser {

    @Override
    public void parse(IResourceEMLoadContext context, Element element) {
        String id = element.getAttribute("id");
        DataSourceConfig ds = new DataSourceConfig();

        String beanName = element.getAttribute("beanName");
        if (!CoreUtils.isBlank(beanName)) {
            ds.setBeanName(beanName);
        }

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

        String shell = element.getAttribute("shell");
        if (!CoreUtils.isBlank(shell)) {
            ds.setShell(shell);
        }

        String database = element.getAttribute("database");
        if (!CoreUtils.isBlank(database)) {
            ds.setDatabase(database);
        }

        String encrypt = element.getAttribute("encrypt");
        if (!CoreUtils.isBlank(encrypt)) {
            ds.setEncrypt(CoreUtils.string2Boolean(encrypt));
        }

        String useJdbc = element.getAttribute("useJdbc");
        if (!CoreUtils.isBlank(useJdbc)) {
            ds.setUseJdbc(CoreUtils.string2Boolean(useJdbc));
        }

        String encoding = element.getAttribute("encoding");
        if (!CoreUtils.isBlank(encoding)) {
            ds.setEncoding(encoding);
        }

        DataSourceManager.register(id, ds);
    }

}
