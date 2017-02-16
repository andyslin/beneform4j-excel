package com.forms.beneform4j.excel.core.model.em.base;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.core.model.em.EMType;
import com.forms.beneform4j.excel.core.model.em.IEM;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 基本Excel模型的实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class BaseEM implements IEM {

    /**
     * 
     */
    private static final long serialVersionUID = 557919262701734009L;

    private String id;

    private String name;

    private EMType type;

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
    public EMType getType() {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void setType(EMType type) {
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
