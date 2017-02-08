package com.forms.beneform4j.excel.core.model.em.tree;

import java.io.Serializable;
import java.util.List;

public interface ITreeEMSheet extends Serializable {

    /**
     * 获取所属的Excel模型
     * 
     * @return
     */
    public ITreeEM getWorkbook();

    /**
     * 获取所有区域列表
     * 
     * @return
     */
    public List<ITreeEMRegion> getRegions();

    /**
     * 获取表单名称
     * 
     * @return
     */
    public String getSheetName();

    /**
     * 获取生成表单的条件
     * 
     * @return
     */
    public String getCondition();

    /**
     * 获取数据的表达式
     * 
     * @return
     */
    public String getExpression();
}
