package com.forms.beneform4j.excel.core.model.em.bean.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.forms.beneform4j.excel.core.model.em.base.BaseEM;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEM;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMProperty;

public class BeanEM extends BaseEM implements IBeanEM {

    /**
     * 
     */
    private static final long serialVersionUID = 8211001197275411821L;

    private Class<?> beanType = LinkedHashMap.class;

    private Map<String, IBeanEMProperty> properties;

    public Class<?> getBeanType() {
        return beanType;
    }

    public void setBeanType(Class<?> beanType) {
        this.beanType = beanType;
    }

    public Map<String, IBeanEMProperty> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, IBeanEMProperty> properties) {
        this.properties = properties;
    }
}
