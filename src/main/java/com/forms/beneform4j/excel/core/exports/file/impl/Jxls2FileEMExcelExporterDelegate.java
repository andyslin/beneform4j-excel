package com.forms.beneform4j.excel.core.exports.file.impl;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.formula.FastFormulaProcessor;
import org.jxls.formula.StandardFormulaProcessor;
import org.jxls.transform.TransformationConfig;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;

import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.core.exports.file.IExcelExporterDelegate;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 使用Jxls2类库实现的Excel导出代理<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class Jxls2FileEMExcelExporterDelegate implements IExcelExporterDelegate {

    @Override
    public boolean export(Object param, Object data, Workbook workbook) throws Exception {
        JxlsHelper instance = JxlsHelper.getInstance();
        Transformer transformer = createTransformer(workbook);
        TransformationConfig transformationConfig = transformer.getTransformationConfig();
        customTransformationConfig(transformationConfig);

        AreaBuilder areaBuilder = instance.getAreaBuilder();
        areaBuilder.setTransformer(transformer);
        List<Area> xlsAreaList = areaBuilder.build();
        if (null == xlsAreaList || xlsAreaList.isEmpty()) {
            return false;
        } else {
            Context context = new Context();
            if (null != param) {
                context.putVar(ExcelComponentConfig.getParamObjectVarName(), param);
            }
            context.putVar(ExcelComponentConfig.getDataObjectVarName(), data);
            for (Area xlsArea : xlsAreaList) {
                xlsArea.applyAt(new CellRef(xlsArea.getStartCellRef().getCellName()), context);
                if (instance.isProcessFormulas()) {
                    setFormulaProcessor(instance, xlsArea);
                    xlsArea.processFormulas();
                }
            }
            // transformer.write();
        }
        return true;
    }

    protected Transformer createTransformer(Workbook workbook) {
        if (workbook instanceof XSSFWorkbook) {
            return PoiTransformer.createSxssfTransformer(workbook, 1000, true);
        } else if (workbook instanceof SXSSFWorkbook) {
            return createTransformer(((SXSSFWorkbook) workbook).getXSSFWorkbook());
        } else {
            return PoiTransformer.createTransformer(workbook);
        }
    }

    protected void customTransformationConfig(TransformationConfig config) {

    }

    private Area setFormulaProcessor(JxlsHelper instance, Area xlsArea) {
        if (instance.isUseFastFormulaProcessor()) {
            xlsArea.setFormulaProcessor(new FastFormulaProcessor());
        } else {
            xlsArea.setFormulaProcessor(new StandardFormulaProcessor());
        }
        return xlsArea;
    }
}
