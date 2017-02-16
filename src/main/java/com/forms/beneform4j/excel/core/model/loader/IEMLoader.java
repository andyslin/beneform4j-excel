package com.forms.beneform4j.excel.core.model.loader;

import com.forms.beneform4j.excel.core.model.em.IEM;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Excel模型加载器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2016-12-22<br>
 */
public interface IEMLoader {

    /**
     * 加载Excel模型
     * 
     * @param modelId 模型ID
     * @return Excel模型
     */
    public IEM load(String modelId);

    /**
     * 移除Excel模型
     * 
     * @param modelId 模型ID
     */
    public void remove(String modelId);

    /**
     * 清空Excel模型
     */
    public void clear();
}
