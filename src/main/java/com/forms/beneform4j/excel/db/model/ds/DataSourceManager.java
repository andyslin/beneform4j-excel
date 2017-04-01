package com.forms.beneform4j.excel.db.model.ds;

import java.util.HashMap;
import java.util.Map;

import com.forms.beneform4j.core.util.CoreUtils;

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

    private static final DataSourceConfig DEFAULT_CONFIG = new DataSourceConfig();

    private static final Map<String, DataSourceConfig> map = new HashMap<String, DataSourceConfig>();

    static {
        DEFAULT_CONFIG.setUseJdbc(true);
    }

    public static void register(String id, DataSourceConfig ds) {
        map.put(id, ds);
    }

    public static DataSourceConfig getDataSource(String id) {
        if (CoreUtils.isBlank(id)) {
            return DEFAULT_CONFIG;
        }
        return map.get(id);
    }
}
