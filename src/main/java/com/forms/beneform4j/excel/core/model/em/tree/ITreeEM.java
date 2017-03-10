package com.forms.beneform4j.excel.core.model.em.tree;

import java.util.List;

import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.em.text.ITextEM;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 树型配置模型接口<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public interface ITreeEM extends IEM {

    /**
     * 获取Sheet配置列表
     * 
     * @return
     */
    public List<ITreeEMSheet> getSheets();

    /**
     * 获取用于转换为文件的文件模型配置
     * 
     * @return
     */
    public ITextEM getTextWorkbook();

    /**
     * 获取第一个组件
     * 
     * @return
     */
    public ITreeEMComponent getFirstComponent();
}
