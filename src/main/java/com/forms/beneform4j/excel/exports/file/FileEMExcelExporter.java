package com.forms.beneform4j.excel.exports.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.jxls.common.Context;
import org.jxls.expression.ExpressionEvaluator;
import org.jxls.transform.TransformationConfig;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.data.accessor.IDataAccessor;
import com.forms.beneform4j.excel.model.file.IFileEM;

public class FileEMExcelExporter extends AbstractFileEMExcelExporter {

    private static final String accessor_var_name = FileEMExcelExporter.class.getName() + ".accessor";

    private static final ExpressionEvaluator evaluator = new ExpressionEvaluator() {
        @Override
        public Object evaluate(String expression, Map<String, Object> context) {
            IDataAccessor accessor = (IDataAccessor) context.remove(accessor_var_name);
            accessor.addVars(context);
            return accessor.value(expression);
        }
    };

    @Override
    protected void export(IFileEM model, IDataAccessor accessor, OutputStream output) {
        InputStream is = null;
        try {
            is = model.getInputStream();
            JxlsHelper instance = JxlsHelper.getInstance();
            Transformer transformer = instance.createTransformer(is, output);
            TransformationConfig transformationConfig = transformer.getTransformationConfig();
            transformationConfig.setExpressionEvaluator(evaluator);
            customTransformationConfig(transformationConfig);
            Context context = new Context();
            context.putVar(accessor_var_name, accessor);
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
