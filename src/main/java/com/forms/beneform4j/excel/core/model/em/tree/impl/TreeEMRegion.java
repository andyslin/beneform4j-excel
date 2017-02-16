package com.forms.beneform4j.excel.core.model.em.tree.impl;

import com.forms.beneform4j.excel.core.model.em.tree.ITreeEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMSheet;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 树型配置模型的区域实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
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

    private String expression;

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
    public String getExpression() {
        return expression;
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

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setComponent(ITreeEMComponent component) {
        this.component = component;
    }
}
