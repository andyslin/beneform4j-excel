package com.forms.beneform4j.excel.core.model.em.bean.impl;

import java.util.HashMap;
import java.util.Map;

import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMExtractor;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMMatcher;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMValidator;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Bean模型的管理类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class BeanEMManager {

    private static final Map<String, IBeanEMExtractor> extractors = new HashMap<String, IBeanEMExtractor>();

    private static final Map<String, IBeanEMMatcher> matchers = new HashMap<String, IBeanEMMatcher>();

    private static final Map<String, IBeanEMValidator> validators = new HashMap<String, IBeanEMValidator>();

    /**
     * 获取提取器
     * 
     * @param id
     * @return
     */
    public static IBeanEMExtractor getExtractor(String id) {
        return extractors.get(id);
    }

    /**
     * 注册提取器
     * 
     * @param id
     * @param extractor
     */
    public static void registerExtractor(String id, IBeanEMExtractor extractor) {
        extractors.put(id, extractor);
    }

    /**
     * 获取匹配器
     * 
     * @param id
     * @return
     */
    public static IBeanEMMatcher getMatcher(String id) {
        return matchers.get(id);
    }

    /**
     * 注册匹配器
     * 
     * @param id
     * @param matcher
     */
    public static void registerMatcher(String id, IBeanEMMatcher matcher) {
        matchers.put(id, matcher);
    }

    /**
     * 获取校验器
     * 
     * @param id
     * @return
     */
    public static IBeanEMValidator getValidator(String id) {
        return validators.get(id);
    }

    /**
     * 注册校验器
     * 
     * @param id
     * @param validator
     */
    public static void registerValidator(String id, IBeanEMValidator validator) {
        validators.put(id, validator);
    }
}
