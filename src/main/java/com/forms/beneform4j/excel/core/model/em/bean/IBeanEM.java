package com.forms.beneform4j.excel.core.model.em.bean;

import java.util.Map;

import com.forms.beneform4j.excel.core.model.em.IEM;

public interface IBeanEM extends IEM {

    public Map<String, IBeanEMProperty> getProperties();

    public Class<?> getBeanType();
}
