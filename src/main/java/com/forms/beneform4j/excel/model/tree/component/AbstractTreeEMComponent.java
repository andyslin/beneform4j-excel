package com.forms.beneform4j.excel.model.tree.component;

import com.forms.beneform4j.excel.model.tree.ITreeEM;
import com.forms.beneform4j.excel.model.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.model.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.model.tree.ITreeEMSheet;

public abstract class AbstractTreeEMComponent implements ITreeEMComponent {

    /**
     * 
     */
    private static final long serialVersionUID = 4470942897384922903L;

    private ITreeEMRegion region;

    @Override
    public ITreeEM getWorkbook() {
        ITreeEMRegion region = getRegion();
        return null == region ? null : region.getWorkbook();
    }

    @Override
    public ITreeEMSheet getSheet() {
        ITreeEMRegion region = getRegion();
        return null == region ? null : region.getSheet();
    }

    @Override
    public ITreeEMRegion getRegion() {
        return region;
    }

    public void setRegion(ITreeEMRegion region) {
        this.region = region;
    }
}
