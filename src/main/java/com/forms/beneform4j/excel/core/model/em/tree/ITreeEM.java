package com.forms.beneform4j.excel.core.model.em.tree;

import java.util.List;

import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.em.text.ITextEM;

public interface ITreeEM extends IEM {

    /**
     * 获取Sheet列表
     * 
     * @return
     */
    public List<ITreeEMSheet> getSheets();

    /**
     * 获取用于转换为文件的文件模型
     * 
     * @return
     */
    public ITextEM getTextWorkbook();
}
