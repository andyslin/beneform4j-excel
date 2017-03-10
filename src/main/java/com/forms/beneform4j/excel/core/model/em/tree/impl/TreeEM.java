package com.forms.beneform4j.excel.core.model.em.tree.impl;

import java.util.List;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.model.em.EMManager;
import com.forms.beneform4j.excel.core.model.em.EMType;
import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.em.base.BaseEM;
import com.forms.beneform4j.excel.core.model.em.text.ITextEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMSheet;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 树型配置模型实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class TreeEM extends BaseEM implements ITreeEM {

    /**
     * 
     */
    private static final long serialVersionUID = -5158072882864528256L;

    private List<ITreeEMSheet> sheets;

    private ITextEM textWorkbook; // 用于转换为上传文件的文件模型

    private String textWorkbookRef;// 用于转换为上传文件的文件模型ID

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
    public ITreeEMComponent getFirstComponent() {
        List<ITreeEMSheet> sheets = getSheets();
        List<ITreeEMRegion> regions = sheets.get(0).getRegions();
        ITreeEMComponent component = regions.get(0).getComponent();
        return component;
    }

    @Override
    public ITextEM getTextWorkbook() {
        if (null == textWorkbook && null != textWorkbookRef) {
            synchronized (this) {
                if (null == textWorkbook && null != textWorkbookRef) {
                    IEM textEM = EMManager.load(textWorkbookRef);
                    if (null == textEM) {
                        Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS07, textWorkbookRef);
                    } else if (!(textEM instanceof ITextEM)) {
                        Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS08, textEM);
                    } else {
                        this.textWorkbook = (ITextEM) textEM;
                    }
                }
            }
        }
        return textWorkbook;
    }

    public void setTextWorkbook(ITextEM textWorkbook) {
        this.textWorkbook = textWorkbook;
    }

    public String getTextWorkbookRef() {
        return textWorkbookRef;
    }

    public void setTextWorkbookRef(String textWorkbookRef) {
        this.textWorkbookRef = textWorkbookRef;
    }

    @Override
    public EMType getType() {
        return EMType.TREE;
    }
}
