package com.forms.beneform4j.excel.core.model.em.tree.impl;

import java.util.List;

import com.forms.beneform4j.excel.core.model.em.tree.ITreeEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMSheet;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 树型配置模型的Sheet表单配置实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class TreeEMSheet implements ITreeEMSheet {

    /**
     * 
     */
    private static final long serialVersionUID = -3632799268161380967L;

    private ITreeEM workbook;

    private String sheetName;

    private List<ITreeEMRegion> regions;

    private String condition;

    private String expression;

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
    public String getExpression() {
        return expression;
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

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
