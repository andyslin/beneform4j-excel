package com.forms.beneform4j.excel.core.imports.stream.impl;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.model.em.text.ITextEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Grid;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 使用树型模型作为参数的回调处理器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class TreeEMWorkbookStreamHandler extends TextEMWorkbookStreamHandler {

    private ITreeEM treeEm;

    public TreeEMWorkbookStreamHandler() {}

    public TreeEMWorkbookStreamHandler(ITreeEM treeEm) {
        super();
        this.treeEm = treeEm;
    }

    @Override
    public void initialize() {
        ITreeEM em = getTreeEm();
        if (null != em) {
            ITextEM textEm = em.getTextWorkbook();
            if (null != textEm) {
                super.initializeWithTextEM(textEm);
            } else {
                this.initializeWithTreeEM(em);
            }
        }
        super.initialize();
    }

    private void initializeWithTreeEM(ITreeEM em) {
        ITreeEMComponent component = em.getFirstComponent();
        if (!(component instanceof Grid)) {
            Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS32, em);
        }

        Grid grid = (Grid) component;

        //super.setCharset(em.getCharset());
        super.setSkipRows(grid.getRowspan() + 1);//标题占一行
        super.setMinCellsOfRow(grid.getColspan());
        super.setDefaultCellValue("");
        //super.setSeparator(em.getSeparator());
        super.setIgnoreEmptyRow(true);
        //super.setBatchNoFormat(em.getBatchNoFormat());
        super.setAddBatchNo(true);
        super.setAddRowIndex(false);
        super.setAddDataIndex(true);
    }

    public ITreeEM getTreeEm() {
        return treeEm;
    }

    public void setTreeEm(ITreeEM treeEm) {
        this.treeEm = treeEm;
    }
}
