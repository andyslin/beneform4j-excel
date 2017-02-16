package com.forms.beneform4j.excel.core.model.em.tree;

import java.io.Serializable;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 树型配置模型的组件配置接口<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public interface ITreeEMComponent extends Serializable {

    /**
     * 获取所属的Excel模型
     * 
     * @return
     */
    public ITreeEM getWorkbook();

    /**
     * 获取所属的表单模型
     * 
     * @return
     */
    public ITreeEMSheet getSheet();

    /**
     * 获取所属的区域
     * 
     * @return
     */
    public ITreeEMRegion getRegion();

    /**
     * 设置所属的区域
     * 
     * @param region
     */
    public void setRegion(ITreeEMRegion region);
}
