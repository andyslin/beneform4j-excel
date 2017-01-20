package com.forms.beneform4j.excel.core.model.em.bean.impl;

import java.util.HashMap;
import java.util.Map;

import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMExtractor;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMMatcher;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMValidator;

public class BeanEMManager {

    private static final Map<String, IBeanEMExtractor> extractors = new HashMap<String, IBeanEMExtractor>();

    private static final Map<String, IBeanEMMatcher> matchers = new HashMap<String, IBeanEMMatcher>();

    private static final Map<String, IBeanEMValidator> validators = new HashMap<String, IBeanEMValidator>();

    public static IBeanEMExtractor getExtractor(String id) {
        return extractors.get(id);
    }

    public static void registerExtractor(String id, IBeanEMExtractor extractor) {
        extractors.put(id, extractor);
    }

    public static IBeanEMMatcher getMatcher(String id) {
        return matchers.get(id);
    }

    public static void registerMatcher(String id, IBeanEMMatcher matcher) {
        matchers.put(id, matcher);
    }

    public static IBeanEMValidator getValidator(String id) {
        return validators.get(id);
    }

    public static void registerValidator(String id, IBeanEMValidator validator) {
        validators.put(id, validator);
    }
}
