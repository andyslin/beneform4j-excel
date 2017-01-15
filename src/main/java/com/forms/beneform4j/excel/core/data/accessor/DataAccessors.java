package com.forms.beneform4j.excel.core.data.accessor;

import java.util.Map;

import com.forms.beneform4j.excel.ExcelComponentConfig;

public class DataAccessors {

    public static IDataAccessor newDataAccessor() {
        return ExcelComponentConfig.getDataAccessorFactory().newDataAccessor();
    }

    public static IDataAccessor newDataAccessor(Object root) {
        return ExcelComponentConfig.getDataAccessorFactory().newDataAccessor(root);
    }

    public static IDataAccessor newDataAccessor(Object root, Map<String, Object> vars) {
        return ExcelComponentConfig.getDataAccessorFactory().newDataAccessor(root, vars);
    }
}
