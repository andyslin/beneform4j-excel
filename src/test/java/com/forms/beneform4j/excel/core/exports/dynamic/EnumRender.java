package com.forms.beneform4j.excel.core.exports.dynamic;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * 注册为Spring的bean
 * 
 * @author lenovo
 *
 */
@Component("enum")
public class EnumRender {

    private static final Map<String, String> datas = new HashMap<String, String>();

    static {
        datas.put("1", "春");
        datas.put("2", "夏");
        datas.put("3", "秋");
        datas.put("4", "冬");
    }

    public String getValue(String code) {
        return datas.get(code);
    }
}
