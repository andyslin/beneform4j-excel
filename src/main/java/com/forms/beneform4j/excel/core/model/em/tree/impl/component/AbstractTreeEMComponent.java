package com.forms.beneform4j.excel.core.model.em.tree.impl.component;

import com.forms.beneform4j.excel.core.model.em.tree.ITreeEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMSheet;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 抽象的树型配置模型的组件实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
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
