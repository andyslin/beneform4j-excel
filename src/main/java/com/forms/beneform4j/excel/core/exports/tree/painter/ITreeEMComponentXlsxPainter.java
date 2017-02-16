package com.forms.beneform4j.excel.core.exports.tree.painter;

import com.forms.beneform4j.core.util.data.accessor.IDataAccessor;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 将组件绘制到Xlsx区域的绘制器接口<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public interface ITreeEMComponentXlsxPainter {

    /**
     * 在Xlsx上绘制组件
     * 
     * @param component 组件
     * @param context 上下文
     * @param accessor 数据访问器
     * @return 绘制的范围
     */
    public Scope paint(ITreeEMComponent component, POIExcelContext context, IDataAccessor accessor);
}
