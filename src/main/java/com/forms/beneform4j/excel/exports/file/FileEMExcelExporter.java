package com.forms.beneform4j.excel.exports.file;

import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.util.ClassUtils;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.exports.base.AbstractWorkbookExcelExporter;
import com.forms.beneform4j.excel.exports.base.BaseExcelExporter;
import com.forms.beneform4j.excel.exports.file.impl.JettFileEMExcelExporterDelegate;
import com.forms.beneform4j.excel.exports.file.impl.Jxls2FileEMExcelExporterDelegate;
import com.forms.beneform4j.excel.model.base.EMType;
import com.forms.beneform4j.excel.model.base.IEM;
import com.forms.beneform4j.excel.model.file.IFileEM;

public class FileEMExcelExporter extends AbstractWorkbookExcelExporter {

    private static final IExcelExporterDelegate defaultJxls2ExporterDelegate;

    private static final IExcelExporterDelegate defaultJettExporterDelegate;

    static {
        ClassLoader classLoader = BaseExcelExporter.class.getClassLoader();
        if (ClassUtils.isPresent("org.jxls.transform.Transformer", classLoader)) {
            defaultJxls2ExporterDelegate = new Jxls2FileEMExcelExporterDelegate();
        } else {
            defaultJxls2ExporterDelegate = null;
        }
        if (ClassUtils.isPresent("net.sf.jett.transform.ExcelTransformer", classLoader)) {
            defaultJettExporterDelegate = new JettFileEMExcelExporterDelegate();
        } else {
            defaultJettExporterDelegate = null;
        }
    }

    private IExcelExporterDelegate jxls2ExporterDelegate = defaultJxls2ExporterDelegate;

    private IExcelExporterDelegate jettExporterDelegate = defaultJettExporterDelegate;

    public void setJxls2ExporterDelegate(IExcelExporterDelegate jxls2ExporterDelegate) {
        this.jxls2ExporterDelegate = jxls2ExporterDelegate;
    }

    public void setJettExporterDelegate(IExcelExporterDelegate jettExporterDelegate) {
        this.jettExporterDelegate = jettExporterDelegate;
    }

    @Override
    protected void export(IEM model, Object param, Object data, Workbook workbook) {
        try {
            EMType type = model.getType();
            if (EMType.EXCEL.equals(type)) {//自动侦测
                type = autoEMType(model, param, data, workbook);
                if (EMType.JXLS2_EXCEL.equals(type)) {//如果是jxls2，说明已经导出，这里直接返回
                    return;
                }
                model.setType(type);
            }

            if (EMType.JETT_EXCEL.equals(type)) {
                jettExporterDelegate.export(param, data, workbook);
            } else if (EMType.JXLS2_EXCEL.equals(type)) {
                jxls2ExporterDelegate.export(param, data, workbook);
            }
        } catch (Exception e) {
            Throw.throwRuntimeException(e);
        }
    }

    @Override
    protected Workbook newWorkbook(IEM model, Object param, Object data) {
        IFileEM fem = checkEM(model);
        InputStream is = null;
        try {
            is = fem.getInputStream();
            Workbook workbook = WorkbookFactory.create(is);
            return workbook;
        } catch (Exception e) {
            throw Throw.createRuntimeException(e);
        } finally {
            CoreUtils.closeQuietly(is);
        }
    }

    private EMType autoEMType(IEM model, Object param, Object data, Workbook workbook) throws Exception {
        EMType type = EMType.JETT_EXCEL;
        if (null != defaultJxls2ExporterDelegate) {//存在jxls2，则用jxls2测试
            try {
                boolean isJxls2 = jxls2ExporterDelegate.export(param, data, workbook);
                type = isJxls2 ? EMType.JXLS2_EXCEL : EMType.JETT_EXCEL;
            } catch (Exception e) {//出现异常，表示是jxls2模板，只是导出时出错
                model.setType(EMType.JXLS2_EXCEL);
                throw e;
            }
        }
        return type;
    }

    private IFileEM checkEM(IEM model) {
        IFileEM fem = null;
        if (model instanceof IFileEM) {
            fem = (IFileEM) model;
        } else {
            Throw.throwRuntimeException("不支持的Excel模型");
        }
        return fem;
    }
}
