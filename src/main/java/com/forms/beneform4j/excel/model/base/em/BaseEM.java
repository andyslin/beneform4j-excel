package com.forms.beneform4j.excel.model.base.em;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.model.base.IEM;

public class BaseEM implements IEM {

    /**
     * 
     */
    private static final long serialVersionUID = 557919262701734009L;

    private String id;

    private String name;

    private String type;

    private String desc;

    private int prior;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDesc() {
        return this.desc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPrior() {
        return this.prior;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPrior(int prior) {
        this.prior = prior;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        String id = getId();
        if (!CoreUtils.isBlank(id)) {
            sb.append(",id:\"").append(id).append("\"");
        }

        String name = getName();
        if (!CoreUtils.isBlank(name)) {
            sb.append(",name:\"").append(name).append("\"");
        }

        String desc = getDesc();
        if (!CoreUtils.isBlank(desc)) {
            sb.append(",desc:\"").append(desc).append("\"");
        }
        return getType() + ": {" + sb.substring(1) + "}";
    }
}
