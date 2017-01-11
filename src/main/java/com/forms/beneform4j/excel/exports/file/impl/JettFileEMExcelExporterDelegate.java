package com.forms.beneform4j.excel.exports.file.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.exports.file.IExcelExporterDelegate;

import net.sf.jett.transform.ExcelTransformer;

public class JettFileEMExcelExporterDelegate implements IExcelExporterDelegate {

    protected ExcelTransformer getTransformer() {
        return new ExcelTransformer();
    }

    @Override
    public boolean export(Object param, Object data, Workbook workbook) throws Exception {
        ExcelTransformer transformer = getTransformer();
        customTransformation(transformer);
        if (data instanceof JettMultiResultData) {
            JettMultiResultData jd = (JettMultiResultData) data;
            List<String> templateSheetNamesList = jd.getTemplateSheetNamesList();
            List<String> newSheetNamesList = jd.getDataSheetNamesList();
            List<Map<String, Object>> beansList = jd.getDatasList();
            if (templateSheetNamesList.isEmpty()) {
                String templateSheetName = workbook.getSheetName(0);
                jd.fillTemplateSheetNamesList(templateSheetName);
            }
            transformer.transform(workbook, templateSheetNamesList, newSheetNamesList, beansList);
        } else {
            Map<String, Object> context = new HashMap<String, Object>();
            context.put(ExcelComponentConfig.getParamObjectVarName(), param);
            context.put(ExcelComponentConfig.getDataObjectVarName(), data);
            transformer.transform(workbook, context);
        }
        return true;
    }

    protected void customTransformation(ExcelTransformer transformer) {

    }
}
