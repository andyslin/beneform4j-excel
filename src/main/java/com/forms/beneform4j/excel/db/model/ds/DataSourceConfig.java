package com.forms.beneform4j.excel.db.model.ds;

import java.io.Serializable;

public class DataSourceConfig implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2555232684590264646L;

    private String username;

    private String password;

    private String tnsname;

    private String encoding;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
