package com.forms.beneform4j.excel.core.model.em.bean;

import java.util.Map;

import com.forms.beneform4j.excel.core.model.em.IEM;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Bean模型接口<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public interface IBeanEM extends IEM {

    /**
     * 获取所有Bean模型的属性配置
     * 
     * @return
     */
    public Map<String, IBeanEMProperty> getProperties();

    /**
     * 获取Bean模型的JavaBean类
     * 
     * @return
     */
    public Class<?> getBeanType();
}
