package com.forms.beneform4j.excel.core.imports.stream.impl;

import com.forms.beneform4j.excel.core.model.em.text.ITextEM;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 使用文本模型作为参数的回调处理器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class TextEMWorkbookStreamHandler extends TextWorkbookStreamHandler {

    private ITextEM textEm;

    public TextEMWorkbookStreamHandler() {}

    public TextEMWorkbookStreamHandler(ITextEM textEm) {
        super();
        this.textEm = textEm;
    }

    @Override
    public void initialize() {
        ITextEM em = getTextEm();
        initializeWithTextEM(em);
        super.initialize();
    }

    protected void initializeWithTextEM(ITextEM em) {
        if (em == null) {
            return;
        }
        super.setCharset(em.getCharset());
        super.setSkipRows(em.getSkipRows());
        super.setMinCellsOfRow(em.getMinCellsOfRow());
        super.setDefaultCellValue(em.getDefaultCellValue());
        super.setSeparator(em.getSeparator());
        super.setIgnoreEmptyRow(em.isIgnoreEmptyRow());
        super.setBatchNoFormat(em.getBatchNoFormat());
        super.setAddBatchNo(em.isAddBatchNo());
        super.setAddRowIndex(em.isAddRowIndex());
        super.setAddDataIndex(em.isAddDataIndex());
    }

    public ITextEM getTextEm() {
        return textEm;
    }

    public void setTextEm(ITextEM textEm) {
        this.textEm = textEm;
    }
}
