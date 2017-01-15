package com.forms.beneform4j.excel.core.data.loader.impl;

import com.forms.beneform4j.excel.core.data.loader.IDataLoader;

public class ImmutableDataDataLoader implements IDataLoader {

    private final Object data;

    public ImmutableDataDataLoader(Object data) {
        this.data = data;
    }

    @Override
    public Object load(Object param) {
        return data;
    }
}
