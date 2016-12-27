package com.forms.beneform4j.excel.model.base.em;

import com.forms.beneform4j.excel.model.base.IEM;

public class BaseEM implements IEM {

    /**
     * 
     */
    private static final long serialVersionUID = 557919262701734009L;

    private String id;

    private String name;

    private String desc;

    private int prior;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

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

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPrior(int prior) {
        this.prior = prior;
    }
}
