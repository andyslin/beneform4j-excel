package com.forms.beneform4j.excel.data.accessor.impl;

import java.util.Map;

import com.forms.beneform4j.excel.data.accessor.IDataAccessor;

public class OgnlDataAccessorFactory extends AbstractDataAccessorFactory {

    @Override
    public IDataAccessor newDataAccessor(Object root, Map<String, Object> vars) {
        return new OgnlDataAccessor(root, vars);
    }

}
