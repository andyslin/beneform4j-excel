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

    /**
     * 表示Spring中配置的DataSource的Bean名称
     */
    private String beanName;

    /**
     * 是否使用JDBC导入
     */
    private boolean useJdbc = false;

    /**
     * 数据库用户名称
     */
    private String username;

    /**
     * 数据库用户密码
     */
    private String password;

    /**
     * 数据库密码是否加密存储
     */
    private boolean encrypt = true;

    /**
     * 数据库tnsname
     */
    private String tnsname;

    /**
     * 数据库字符集
     */
    private String encoding;

    /**
     * 数据库类型
     */
    private String database;

    /**
     * 导入数据库的命令脚本
     */
    private String shell;

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

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public boolean isUseJdbc() {
        return useJdbc;
    }

    public void setUseJdbc(boolean useJdbc) {
        this.useJdbc = useJdbc;
    }
}
