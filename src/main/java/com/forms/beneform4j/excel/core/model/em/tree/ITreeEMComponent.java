package com.forms.beneform4j.excel.core.model.em.tree;

import java.io.Serializable;

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
