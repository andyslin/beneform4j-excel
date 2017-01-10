package com.forms.beneform4j.excel.exports.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.jxls.common.Context;
import org.jxls.transform.TransformationConfig;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.model.file.IFileEM;

public class FileEMExcelExporter extends AbstractFileEMExcelExporter {

    @Override
    protected void export(IFileEM model, Object param, Object data, OutputStream output) {
        InputStream is = null;
        try {
            is = model.getInputStream();
            JxlsHelper instance = JxlsHelper.getInstance();
            Transformer transformer = instance.createTransformer(is, output);
            TransformationConfig transformationConfig = transformer.getTransformationConfig();
            customTransformationConfig(transformationConfig);
            Context context = new Context();
            context.putVar(ExcelComponentConfig.getParamObjectVarName(), param);
            context.putVar(ExcelComponentConfig.getDataObjectVarName(), data);
            instance.processTemplate(context, transformer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CoreUtils.closeQuietly(is);
        }
    }

    protected void customTransformationConfig(TransformationConfig config) {

    }
}
