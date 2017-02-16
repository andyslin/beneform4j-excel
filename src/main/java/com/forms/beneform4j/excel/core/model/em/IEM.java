package com.forms.beneform4j.excel.core.model.em;

import java.io.Serializable;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Excel模型<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2016-12-22<br>
 */
public interface IEM extends Serializable {

    /**
     * 获取模型ID
     * 
     * @return 模型ID
     */
    public String getId();

    /**
     * 获取模型名称
     * 
     * @return 模型名称
     */
    public String getName();

    /**
     * 获取模型类别
     * 
     * @return 模型类别
     */
    public EMType getType();

    /**
     * 获取模型描述
     * 
     * @return 模型描述
     */
    public String getDesc();

    /**
     * 获取模型优先级，数值越小，优先级越高，优先级用于处理相同ID多个模型时的冲突
     * 
     * @return 模型优先级
     */
    public int getPrior();

    /**
     * 设置模型类别
     * 
     * @param type
     */
    public void setType(EMType type);
}
