package com.forms.beneform4j.excel.exports.tree.painter;

import com.forms.beneform4j.excel.data.accessor.IDataAccessor;
import com.forms.beneform4j.excel.model.tree.ITreeEMComponent;

public interface ITreeEMComponentXlsxPainter {

    /**
     * 在Xlsx上绘制组件
     * 
     * @param component
     * @param context
     * @param accessor
     * @return
     */
    public Scope paint(ITreeEMComponent component, POIExcelContext context, IDataAccessor accessor);
}
