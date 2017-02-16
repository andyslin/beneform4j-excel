package com.forms.beneform4j.excel.core.model.loader;

import org.springframework.core.io.Resource;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 从Spring的Resource对象中加载Excel模型的上下文<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public interface IResourceEMLoadContext extends IEMLoadContext {

    /**
     * 获取资源对象
     * 
     * @return
     */
    public Resource getResource();
}
