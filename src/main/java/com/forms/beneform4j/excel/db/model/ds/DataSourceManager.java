package com.forms.beneform4j.excel.db.model.ds;

import java.util.HashMap;
import java.util.Map;

public class DataSourceManager {

    private static final Map<String, DataSourceConfig> map = new HashMap<String, DataSourceConfig>();

    public static void register(String id, DataSourceConfig ds) {
        map.put(id, ds);
    }

    public static DataSourceConfig getDataSource(String id) {
        return map.get(id);
    }
}
