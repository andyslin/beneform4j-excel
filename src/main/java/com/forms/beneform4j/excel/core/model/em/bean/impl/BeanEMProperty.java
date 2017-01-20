package com.forms.beneform4j.excel.core.model.em.bean.impl;

import com.forms.beneform4j.excel.core.model.em.bean.IBeanEM;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMExtractor;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMMatcher;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMProperty;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMValidator;
import com.forms.beneform4j.excel.core.model.em.bean.impl.extractor.DefaultBeanEMExtractor;

/**
 * 版权：深圳四方精创资讯股份有限公司 功能：实现类－属性级别元数据简单实现类 作者： LinJisong 版本： 1.0.0 日期： 2015-11-16
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
