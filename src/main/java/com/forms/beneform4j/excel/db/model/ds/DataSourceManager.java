package com.forms.beneform4j.excel.db.model.ds;

import java.util.HashMap;
import java.util.Map;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 数据源配置管理类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-3-7<br>
 */
public class DataSourceManager {

    private static final Map<String, DataSourceConfig> map = new HashMap<String, DataSourceConfig>();

    public static void register(String id, DataSourceConfig ds) {
        map.put(id, ds);
    }

    public static DataSourceConfig getDataSource(String id) {
        return map.get(id);
    }
}
