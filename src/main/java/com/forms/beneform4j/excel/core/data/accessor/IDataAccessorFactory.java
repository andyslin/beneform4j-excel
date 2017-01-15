package com.forms.beneform4j.excel.core.data.accessor;

import java.util.Map;

public interface IDataAccessorFactory {

    public IDataAccessor newDataAccessor();

    public IDataAccessor newDataAccessor(Object root);

    public IDataAccessor newDataAccessor(Object root, Map<String, Object> vars);
}
