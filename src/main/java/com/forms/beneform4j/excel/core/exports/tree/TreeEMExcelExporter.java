package com.forms.beneform4j.excel.core.exports.tree;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.data.accessor.IDataAccessor;
import com.forms.beneform4j.core.util.logger.CommonLogger;
import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.core.exports.tree.painter.ITreeEMComponentXlsxPainter;
import com.forms.beneform4j.excel.core.exports.tree.painter.POIExcelContext;
import com.forms.beneform4j.excel.core.exports.tree.painter.Scope;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMSheet;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 使用XML中tree-workbook作为模板的Excel导出实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class TreeEMExcelExporter extends AbstractTreeEMExcelExporter {

    @Override
    protected void export(ITreeEM model, IDataAccessor accessor, Workbook workbook) {
        // 上下文
        POIExcelContext context = new POIExcelContext(workbook, false);
        // 循环处理每个Sheet配置
        List<ITreeEMSheet> emSheets = model.getSheets();
        for (ITreeEMSheet emSheet : emSheets) {
            IDataAccessor sheetProvider = accessor.derive(emSheet.getExpression());
            if (sheetProvider.match(emSheet.getCondition())) {
                paintSheet(sheetProvider, context, emSheet);
            }
        }
    }

    private void paintSheet(IDataAccessor accessor, POIExcelContext context, ITreeEMSheet emSheet) {
        // 创建新的Sheet表单
        context.createNewSheet(emSheet.getSheetName());
        // 循环处理每个区域配置
        List<ITreeEMRegion> regions = emSheet.getRegions();
        for (ITreeEMRegion region : regions) {
            IDataAccessor regionAccessor = accessor.derive(region.getExpression());
            if (regionAccessor.match(region.getCondition())) {
                ITreeEMComponent component = region.getComponent();
                ITreeEMComponentXlsxPainter painter = ExcelComponentConfig.getXlsxPainter(component);
                if (null != painter) {
                    Scope scope = painter.paint(component, context, regionAccessor);
                    if (null != scope) {
                        context.setLastScope(scope);
                        String name = region.getName();
                        String r = "";
                        if (!CoreUtils.isBlank(name)) {
                            context.addScope(name, scope);
                            r = "[" + name + "]";
                        }
                        CommonLogger.info("Region" + r + " painted in " + scope);
                    }
                }
            }
        }
    }

}
