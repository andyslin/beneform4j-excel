package com.forms.beneform4j.excel.core.model.em.bean;

import java.io.Serializable;

public interface IBeanEMProperty extends Serializable {

    /**
     * 属性名称
     * 
     * @return
     */
    public String getName();

    /**
     * 属性类型
     * 
     * @return
     */
    public Class<?> getType();

    /**
     * 匹配器，用于定位
     * 
     * @return
     */
    public IBeanEMMatcher getMatcher();

    /**
     * 校验器
     * 
     * @return
     */
    public IBeanEMValidator getValidator();

    /**
     * 数据提取器
     * 
     * @return
     */
    public IBeanEMExtractor getExtractor();

    /**
     * 匹配结束的匹配器 只用于集合或嵌套对象的类型
     * 
     * @return
     */
    public IBeanEMMatcher getEndMatcher();

    /**
     * 表示集合元素类型的Excel模型，只用于集合或嵌套对象的类型
     * 
     * @return
     */
    public IBeanEM getInnerBeanEM();

    /**
     * 说明
     * 
     * @return
     */
    public String getDesc();
}
