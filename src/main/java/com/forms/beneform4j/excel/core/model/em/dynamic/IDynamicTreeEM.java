package com.forms.beneform4j.excel.core.model.em.dynamic;

import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEM;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 动态树型配置模型接口<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public interface IDynamicTreeEM extends IEM {

    /**
     * 应用参数，转换为树型配置模型
     * 
     * @param param 参数
     * @param data 数据
     * @return 树型模型配置
     */
    public ITreeEM apply(Object param, Object data);
}
