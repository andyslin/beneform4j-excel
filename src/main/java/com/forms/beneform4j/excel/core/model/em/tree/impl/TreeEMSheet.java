package com.forms.beneform4j.excel.core.model.em.tree.impl;

import java.util.List;

import com.forms.beneform4j.excel.core.model.em.tree.ITreeEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMSheet;

public class TreeEMSheet implements ITreeEMSheet {

    /**
     * 
     */
    private static final long serialVersionUID = -3632799268161380967L;

    private ITreeEM workbook;

    private String sheetName;

    private List<ITreeEMRegion> regions;

    private String condition;

    private String property;

    @Override
    public ITreeEM getWorkbook() {
        return workbook;
    }

    @Override
    public List<ITreeEMRegion> getRegions() {
        return regions;
    }

    @Override
    public String getSheetName() {
        return sheetName;
    }

    @Override
    public String getCondition() {
        return condition;
    }

    @Override
    public String getProperty() {
        return property;
    }

    public void setWorkbook(ITreeEM workbook) {
        this.workbook = workbook;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public void setRegions(List<ITreeEMRegion> regions) {
        this.regions = regions;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
