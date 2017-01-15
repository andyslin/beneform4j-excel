package com.forms.beneform4j.excel.core.model.em.tree.impl;

import java.util.List;

import com.forms.beneform4j.excel.core.model.em.EMType;
import com.forms.beneform4j.excel.core.model.em.base.BaseEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMSheet;

public class TreeEM extends BaseEM implements ITreeEM {

    /**
     * 
     */
    private static final long serialVersionUID = -5158072882864528256L;

    private List<ITreeEMSheet> sheets;

    public TreeEM() {
        super.setType(EMType.TREE);
    }

    @Override
    public List<ITreeEMSheet> getSheets() {
        return sheets;
    }

    public void setSheets(List<ITreeEMSheet> sheets) {
        this.sheets = sheets;
    }

    @Override
    public EMType getType() {
        return EMType.TREE;
    }
}
