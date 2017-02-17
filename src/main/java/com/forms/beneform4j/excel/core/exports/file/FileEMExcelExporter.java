package com.forms.beneform4j.excel.core.exports.file;

import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.util.ClassUtils;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.exports.base.AbstractWorkbookExcelExporter;
import com.forms.beneform4j.excel.core.exports.base.BaseExcelExporter;
import com.forms.beneform4j.excel.core.exports.file.impl.JettFileEMExcelExporterDelegate;
import com.forms.beneform4j.excel.core.exports.file.impl.Jxls2FileEMExcelExporterDelegate;
import com.forms.beneform4j.excel.core.model.em.EMType;
import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.em.file.IFileEM;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 使用Excel文件作为模板的Excel导出实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class FileEMExcelExporter extends AbstractWorkbookExcelExporter {

    private static final IExcelExporterDelegate defaultJxls2ExporterDelegate;

    private static final IExcelExporterDelegate defaultJettExporterDelegate;

    static {
        ClassLoader classLoader = BaseExcelExporter.class.getClassLoader();
        if (ClassUtils.isPresent("org.jxls.transform.poi.PoiTransformer", classLoader)) {
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
        EMType type = model.getType();
        if (EMType.EXCEL.equals(type)) {//自动侦测
            type = autoEMType(model, param, data, workbook);
            model.setType(type);
            if (EMType.JXLS2_EXCEL.equals(type)) {//如果是jxls2，说明已经导出，这里直接返回
                return;
            }
        }

        if (EMType.JETT_EXCEL.equals(type)) {
            try {
                jettExporterDelegate.export(param, data, workbook);
            } catch (Exception e) {
                Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS29, model);
            }
        } else if (EMType.JXLS2_EXCEL.equals(type)) {
            try {
                jxls2ExporterDelegate.export(param, data, workbook);
            } catch (Exception e) {
                Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS28, model);
            }
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
            throw Throw.createRuntimeException(ExcelExceptionCodes.BF0XLS01, e);
        } finally {
            CoreUtils.closeQuietly(is);
        }
    }

    private EMType autoEMType(IEM model, Object param, Object data, Workbook workbook) {
        EMType type = EMType.JETT_EXCEL;
        if (null != defaultJxls2ExporterDelegate) {//存在jxls2，则用jxls2测试
            try {
                boolean isJxls2 = jxls2ExporterDelegate.export(param, data, workbook);
                type = isJxls2 ? EMType.JXLS2_EXCEL : EMType.JETT_EXCEL;
            } catch (Exception e) {//出现异常，表示是jxls2模板，只是导出时出错
                model.setType(EMType.JXLS2_EXCEL);
                Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS28, e, model);
            }
        }
        return type;
    }

    private IFileEM checkEM(IEM model) {
        IFileEM fem = null;
        if (model instanceof IFileEM) {
            fem = (IFileEM) model;
        } else {
            Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS08, model);
        }
        return fem;
    }
}
