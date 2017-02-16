package com.forms.beneform4j.excel.core.model.em.tree.impl.component;

import java.util.List;

import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMRegion;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 嵌套区域组件<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
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
