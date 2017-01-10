package com.forms.beneform4j.excel.data.accessor.impl;

import com.forms.beneform4j.excel.data.accessor.IDataAccessor;
import com.forms.beneform4j.excel.data.accessor.IDataAccessorFactory;

public abstract class AbstractDataAccessorFactory implements IDataAccessorFactory {

    @Override
    public IDataAccessor newDataAccessor() {
        return this.newDataAccessor(null, null);
    }

    @Override
    public IDataAccessor newDataAccessor(Object root) {
        return this.newDataAccessor(root, null);
    }
}
