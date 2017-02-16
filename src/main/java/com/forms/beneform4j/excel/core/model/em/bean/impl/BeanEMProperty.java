package com.forms.beneform4j.excel.core.model.em.bean.impl;

import com.forms.beneform4j.excel.core.model.em.bean.IBeanEM;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMExtractor;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMMatcher;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMProperty;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMValidator;
import com.forms.beneform4j.excel.core.model.em.bean.impl.extractor.DefaultBeanEMExtractor;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Bean模型的属性配置实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class BeanEMProperty implements IBeanEMProperty {

    /**
     * 
     */
    private static final long serialVersionUID = 6309650235863101991L;

    private static final IBeanEMExtractor defaultExtractor = new DefaultBeanEMExtractor();

    private String name;

    private Class<?> type;

    private IBeanEMMatcher matcher;

    private IBeanEMExtractor extractor;

    private IBeanEMValidator validator;

    private IBeanEMMatcher endMatcher;

    private IBeanEM innerBeanEM;

    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public IBeanEMMatcher getMatcher() {
        return matcher;
    }

    public void setMatcher(IBeanEMMatcher matcher) {
        this.matcher = matcher;
    }

    public IBeanEMExtractor getExtractor() {
        return extractor == null ? defaultExtractor : extractor;
    }

    public void setExtractor(IBeanEMExtractor extractor) {
        this.extractor = extractor;
    }

    public IBeanEMValidator getValidator() {
        return validator;
    }

    public void setValidator(IBeanEMValidator validator) {
        this.validator = validator;
    }

    public IBeanEMMatcher getEndMatcher() {
        return endMatcher;
    }

    public void setEndMatcher(IBeanEMMatcher endMatcher) {
        this.endMatcher = endMatcher;
    }

    public IBeanEM getInnerBeanEM() {
        return innerBeanEM;
    }

    public void setInnerBeanEM(IBeanEM innerBeanEM) {
        this.innerBeanEM = innerBeanEM;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
