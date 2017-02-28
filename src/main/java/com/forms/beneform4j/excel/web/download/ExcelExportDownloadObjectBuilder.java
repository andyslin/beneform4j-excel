package com.forms.beneform4j.excel.web.download;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import com.forms.beneform4j.core.dao.mybatis.mapper.impl.DataStreamMapperMethodExecutor;
import com.forms.beneform4j.core.dao.stream.IListStreamReader;
import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.exports.ExcelExporters;
import com.forms.beneform4j.excel.core.model.em.EMManager;
import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.em.file.IFileEM;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;
import com.forms.beneform4j.web.springmvc.download.DownloadConsts;
import com.forms.beneform4j.web.springmvc.download.builder.AbstractOutputStreamDownloadObjectBuilder;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 导出Excel数据的下载对象构建器<br>
 * Author : LinJisong <br>
 * Version : 1.1.0 <br>
 * Since : 1.1.0 <br>
 * Date : 2017-2-23<br>
 */
public class ExcelExportDownloadObjectBuilder extends AbstractOutputStreamDownloadObjectBuilder {

    private static final String EXCEL_MODEL_PARAM_NAME = ExcelExportDownloadObjectBuilder.class.getName() + ".EXCEL_MODEL_PARAM_NAME";

    public ExcelExportDownloadObjectBuilder() {
        super.setBuildType(DownloadConsts.DATA_EXPORT_BUILD_TYPE);
    }

    @Override
    protected String getFilename(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        IEM em = this.getExcelModel(request);
        model.put(EXCEL_MODEL_PARAM_NAME, em);
        String filename = request.getParameter(DownloadConsts.DOWNLOAD_TITLE_PARAM_NAME);
        String suffix = request.getParameter(DownloadConsts.DOWNLOAD_SUFFIX_PARAM_NAME);
        if (CoreUtils.isBlank(filename)) {
            filename = em.getName();
        }
        if (CoreUtils.isBlank(suffix)) {
            if (em instanceof IFileEM) {
                String template = ((IFileEM) em).getFilename();
                suffix = FilenameUtils.getExtension(template);
            }

            if (CoreUtils.isBlank(suffix)) {
                suffix = "xlsx";
            }
        }
        return filename + "." + suffix;
    }

    @Override
    protected void doBuild(Map<String, Object> model, HttpServletRequest request, OutputStream out) throws IOException {
        try {
            IEM em = (IEM) model.get(EXCEL_MODEL_PARAM_NAME);
            Object param = this.getParamObject(model, request, em);
            Object data = this.getDataObject(model, request, em);
            ExcelExporters.exports(em, param, data, out);
        } finally {
            model.remove(EXCEL_MODEL_PARAM_NAME);
        }
    }

    protected Object getParamObject(Map<String, Object> model, HttpServletRequest request, IEM em) {
        return model;
    }

    protected Object getDataObject(Map<String, Object> model, HttpServletRequest request, IEM em) {
        IListStreamReader<?> reader = DataStreamMapperMethodExecutor.getDataStreamReader();
        if (em instanceof IFileEM) {//文件模型先读取所有数据
            return reader.readAll();
        } else {
            return reader;
        }
    }

    protected IEM getExcelModel(HttpServletRequest request) {
        IEM em = null;
        String modelId = request.getParameter("downloadId");
        if (!CoreUtils.isBlank(modelId)) {
            em = EMManager.load(modelId);
        }

        // 还可以根据request中的参数构建临时模型

        if (em == null) {
            Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS07, modelId);
        }
        return em;
    }
}
