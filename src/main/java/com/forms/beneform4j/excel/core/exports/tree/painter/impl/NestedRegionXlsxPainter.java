package com.forms.beneform4j.excel.core.exports.tree.painter.impl;

import java.util.List;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.data.accessor.IDataAccessor;
import com.forms.beneform4j.core.util.logger.CommonLogger;
import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.core.exports.tree.painter.ITreeEMComponentXlsxPainter;
import com.forms.beneform4j.excel.core.exports.tree.painter.POIExcelContext;
import com.forms.beneform4j.excel.core.exports.tree.painter.Scope;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.NestedRegionTreeEMComponent;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 嵌套区域组件绘制器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class NestedRegionXlsxPainter extends AbstractSingleXlsxPainter<NestedRegionTreeEMComponent> {

    @Override
    protected Scope doPaint(NestedRegionTreeEMComponent component, POIExcelContext context, IDataAccessor accessor, Scope baseScope) {
        List<ITreeEMRegion> regions = component.getRegions();
        Scope scope = null;
        for (ITreeEMRegion region : regions) {
            IDataAccessor regionAccessor = accessor.derive(region.getExpression());
            if (regionAccessor.match(region.getCondition())) {
                ITreeEMComponent sub = region.getComponent();
                ITreeEMComponentXlsxPainter painter = ExcelComponentConfig.getXlsxPainter(sub);
                if (null != painter) {
                    Scope s = painter.paint(sub, context, regionAccessor);
                    if (null == scope) {
                        scope = s;
                    } else {
                        scope.merge(s);

                        context.setLastScope(s);
                        String name = region.getName();
                        String r = "";
                        if (!CoreUtils.isBlank(name)) {
                            context.addScope(name, s);
                            r = "[" + name + "]";
                        }
                        CommonLogger.info("Region " + r + " painted in " + s);
                    }
                }
            }
        }
        return scope;
    }
}
