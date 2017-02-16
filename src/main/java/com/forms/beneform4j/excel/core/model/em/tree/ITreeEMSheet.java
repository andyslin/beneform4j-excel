package com.forms.beneform4j.excel.core.model.em.tree;

import java.io.Serializable;
import java.util.List;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 树型配置模型的Sheet表单配置接口<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
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
