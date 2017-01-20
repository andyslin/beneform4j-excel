package com.forms.beneform4j.excel.core.exports.tree.painter;

import com.forms.beneform4j.core.util.data.accessor.IDataAccessor;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;

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
