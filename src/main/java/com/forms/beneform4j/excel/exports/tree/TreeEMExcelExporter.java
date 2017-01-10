package com.forms.beneform4j.excel.exports.tree;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.logger.CommonLogger;
import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.data.accessor.IDataAccessor;
import com.forms.beneform4j.excel.exports.tree.painter.ITreeEMComponentXlsxPainter;
import com.forms.beneform4j.excel.exports.tree.painter.POIExcelContext;
import com.forms.beneform4j.excel.exports.tree.painter.Scope;
import com.forms.beneform4j.excel.model.tree.ITreeEM;
import com.forms.beneform4j.excel.model.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.model.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.model.tree.ITreeEMSheet;

public class TreeEMExcelExporter extends AbstractTreeEMExcelExporter {

    @Override
    protected Workbook export(ITreeEM model, IDataAccessor accessor) {
        //XSSFWorkbook wb = new XSSFWorkbook();
        int rowAccessWindowSize = 1000;
        boolean compressTempFiles = true;
        boolean useSharedStringsTable = false;
        Workbook workbook = new SXSSFWorkbook(null, rowAccessWindowSize, compressTempFiles, useSharedStringsTable);

        // 上下文
        POIExcelContext context = new POIExcelContext(workbook, false);
        // 循环处理每个Sheet配置
        List<ITreeEMSheet> emSheets = model.getSheets();
        for (ITreeEMSheet emSheet : emSheets) {
            IDataAccessor sheetProvider = accessor.derive(emSheet.getProperty());
            if (sheetProvider.match(emSheet.getCondition())) {
                paintSheet(sheetProvider, context, emSheet);
            }
        }
        return workbook;
    }

    private void paintSheet(IDataAccessor accessor, POIExcelContext context, ITreeEMSheet emSheet) {
        // 创建新的Sheet表单
        context.createNewSheet(emSheet.getSheetName());
        // 循环处理每个区域配置
        List<ITreeEMRegion> regions = emSheet.getRegions();
        for (ITreeEMRegion region : regions) {
            IDataAccessor regionAccessor = accessor.derive(region.getProperty());
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