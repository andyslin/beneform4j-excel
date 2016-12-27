package com.forms.beneform4j.excel.model.base;

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
     * @return
     */
    public String getId();

    /**
     * 获取模型名称
     * 
     * @return
     */
    public String getName();

    /**
     * 获取模型描述
     * 
     * @return
     */
    public String getDesc();

    /**
     * 获取模型优先级，数值越小，优先级越高，优先级用于处理相同ID多个模型时的冲突
     * 
     * @return
     */
    public int getPrior();
}
