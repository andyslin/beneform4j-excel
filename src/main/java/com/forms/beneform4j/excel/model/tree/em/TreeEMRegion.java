package com.forms.beneform4j.excel.model.tree.em;

import com.forms.beneform4j.excel.model.tree.ITreeEM;
import com.forms.beneform4j.excel.model.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.model.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.model.tree.ITreeEMSheet;

public class TreeEMRegion implements ITreeEMRegion {

    /**
     * 
     */
    private static final long serialVersionUID = -3774506928111061693L;

    private ITreeEMSheet sheet;

    private String name;

    private String title;

    private String offsetName;

    private OffsetPoint offsetPoint;

    private int offsetX;

    private int offsetY;

    private String condition;

    private String property;

    private ITreeEMComponent component;

    /**
     * {@inheritDoc}
     */
    @Override
    public ITreeEM getWorkbook() {
        ITreeEMSheet sheet = getSheet();
        return null == sheet ? null : sheet.getWorkbook();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITreeEMSheet getSheet() {
        return this.sheet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOffsetName() {
        return offsetName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetPoint getOffsetPoint() {
        return offsetPoint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOffsetX() {
        return this.offsetX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOffsetY() {
        return this.offsetY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCondition() {
        return condition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProperty() {
        return property;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITreeEMComponent getComponent() {
        return component;
    }

    public void setSheet(ITreeEMSheet sheet) {
        this.sheet = sheet;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOffsetName(String offsetName) {
        this.offsetName = offsetName;
    }

    public void setOffsetPoint(OffsetPoint offsetPoint) {
        this.offsetPoint = offsetPoint;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setComponent(ITreeEMComponent component) {
        this.component = component;
    }
}
