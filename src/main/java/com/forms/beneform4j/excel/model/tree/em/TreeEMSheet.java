package com.forms.beneform4j.excel.model.tree.em;

import java.util.List;

import com.forms.beneform4j.excel.model.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.model.tree.ITreeEMSheet;

public class TreeEMSheet implements ITreeEMSheet {

    /**
     * 
     */
    private static final long serialVersionUID = -3632799268161380967L;

    private String sheetName;

    private List<ITreeEMRegion> regions;

    @Override
    public List<ITreeEMRegion> getRegions() {
        return regions;
    }

    @Override
    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public void setRegions(List<ITreeEMRegion> regions) {
        this.regions = regions;
    }
}
