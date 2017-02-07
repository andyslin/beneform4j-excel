package com.forms.beneform4j.excel.core.imports.stream.impl;

import java.util.List;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.model.em.text.ITextEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMSheet;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Grid;

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
        List<ITreeEMSheet> sheets = em.getSheets();
        if (null == sheets || sheets.isEmpty()) {
            Throw.throwRuntimeException("目前树型模型导入只支持单个Grid组件的模型");
        }

        List<ITreeEMRegion> regions = sheets.get(0).getRegions();
        if (null == regions || regions.isEmpty()) {
            Throw.throwRuntimeException("目前树型模型导入只支持单个Grid组件的模型");
        }

        ITreeEMComponent component = regions.get(0).getComponent();
        if (!(component instanceof Grid)) {
            Throw.throwRuntimeException("目前树型模型导入只支持单个Grid组件的模型");
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
