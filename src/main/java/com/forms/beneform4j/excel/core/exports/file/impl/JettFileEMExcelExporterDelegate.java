package com.forms.beneform4j.excel.core.exports.file.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.core.expansion.jett.Beneform4jJettTagLibrary;
import com.forms.beneform4j.excel.core.exports.file.IExcelExporterDelegate;

import net.sf.jett.transform.ExcelTransformer;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 使用Jett类库实现的Excel导出代理<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class JettFileEMExcelExporterDelegate implements IExcelExporterDelegate {

    protected ExcelTransformer getTransformer() {
        return new ExcelTransformer();
    }

    @Override
    public boolean export(Object param, Object data, Workbook workbook) throws Exception {
        ExcelTransformer transformer = getTransformer();
        transformer.registerTagLibrary("b", Beneform4jJettTagLibrary.getTagLibrary());
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
            if (null != param) {
                context.put(ExcelComponentConfig.getParamObjectVarName(), param);
            }
            context.put(ExcelComponentConfig.getDataObjectVarName(), data);
            transformer.transform(workbook, context);
        }
        return true;
    }

    protected void customTransformation(ExcelTransformer transformer) {

    }
}
