package com.forms.beneform4j.excel.model.tree.component;

import java.util.List;

import com.forms.beneform4j.excel.model.tree.ITreeEMRegion;

public class NestedRegionTreeEMComponent extends AbstractTreeEMComponent {

    /**
     * 
     */
    private static final long serialVersionUID = 2633932377597158105L;

    private List<ITreeEMRegion> regions;

    public List<ITreeEMRegion> getRegions() {
        return regions;
    }

    public void setRegions(List<ITreeEMRegion> regions) {
        this.regions = regions;
    }
}
