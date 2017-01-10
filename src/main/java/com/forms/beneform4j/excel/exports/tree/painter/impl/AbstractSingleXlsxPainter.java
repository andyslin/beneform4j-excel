package com.forms.beneform4j.excel.exports.tree.painter.impl;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.data.accessor.IDataAccessor;
import com.forms.beneform4j.excel.exports.tree.painter.ITreeEMComponentXlsxPainter;
import com.forms.beneform4j.excel.exports.tree.painter.POIExcelContext;
import com.forms.beneform4j.excel.exports.tree.painter.Scope;
import com.forms.beneform4j.excel.model.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.model.tree.ITreeEMRegion;

public abstract class AbstractSingleXlsxPainter<C extends ITreeEMComponent> implements ITreeEMComponentXlsxPainter {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Scope paint(ITreeEMComponent component, POIExcelContext context, IDataAccessor accessor) {
        if (null == component) {
            return null;
        }
        C c = null;
        try {
            c = (C) component;
        } catch (Exception e) {
            Throw.throwRuntimeException("不支持");
        }
        ITreeEMRegion region = component.getRegion();
        Scope scope = context.getBaseScope(region.getOffsetName());
        return this.doPaint(c, context, accessor, scope);
    }

    /**
     * 绘制组件
     * 
     * @param component 组件
     * @param context 上下文
     * @param accessor 数据访问器
     * @param offsetScope 组件绘制时偏移相对应的基准范围
     * @return
     */
    abstract protected Scope doPaint(C component, POIExcelContext context, IDataAccessor accessor, Scope offsetScope);
}
