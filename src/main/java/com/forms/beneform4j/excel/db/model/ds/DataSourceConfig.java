package com.forms.beneform4j.excel.db.model.ds;

import java.io.Serializable;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.encrypt.IEncrypt;
import com.forms.beneform4j.core.util.encrypt.impl.ConfigKeyEncrypt;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 数据源配置<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-3-7<br>
 */
public class DataSourceConfig implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2555232684590264646L;

    private String username;

    private String password;

    private String tnsname;

    private String encoding;

    private String database;

    private String shell;

    private boolean encrypt = true;

    /**
     * 加密算法
     */
    private IEncrypt encryptAlgorithm = new ConfigKeyEncrypt();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        if (!CoreUtils.isBlank(password) && this.isEncrypt()) {
            return encryptAlgorithm.decode(password, null);
        } else {
            return password;
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShell() {
        return shell;
    }

    public void setShell(String shell) {
        this.shell = shell;
    }

    public String getTnsname() {
        return tnsname;
    }

    public void setTnsname(String tnsname) {
        this.tnsname = tnsname;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public boolean isEncrypt() {
        return encrypt;
    }

    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }
}
