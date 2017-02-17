package com.forms.beneform4j.excel.core.exports.tree.painter.impl;

import com.forms.beneform4j.core.util.data.accessor.IDataAccessor;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.exports.tree.painter.ITreeEMComponentXlsxPainter;
import com.forms.beneform4j.excel.core.exports.tree.painter.POIExcelContext;
import com.forms.beneform4j.excel.core.exports.tree.painter.Scope;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 抽象的单个组件绘制器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
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
            Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS30);
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
