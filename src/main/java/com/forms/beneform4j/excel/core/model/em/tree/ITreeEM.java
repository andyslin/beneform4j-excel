package com.forms.beneform4j.excel.core.model.em.tree;

import java.util.List;

import com.forms.beneform4j.excel.core.model.em.IEM;

public interface ITreeEM extends IEM {

    /**
     * 获取Sheet列表
     * 
     * @return
     */
    public List<ITreeEMSheet> getSheets();
}
