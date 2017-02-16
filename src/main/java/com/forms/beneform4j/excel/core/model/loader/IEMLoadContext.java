package com.forms.beneform4j.excel.core.model.loader;

import com.forms.beneform4j.excel.core.model.em.IEM;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Excel模型加载上下文<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public interface IEMLoadContext {

    /**
     * 向上下文环境中注册Excel模型
     * 
     * @param model Excel模型
     */
    public void register(IEM model);
}
