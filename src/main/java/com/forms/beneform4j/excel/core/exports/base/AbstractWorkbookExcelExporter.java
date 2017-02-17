package com.forms.beneform4j.excel.core.exports.base;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 抽象的Excel导出实现类，实现将Workbook对象输出至流的功能<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public abstract class AbstractWorkbookExcelExporter extends AbstractDataExcelExporter {

    /**
     * 创建Excel对象
     * 
     * @param model
     * @param param
     * @param data
     * @return
     */
    abstract protected Workbook newWorkbook(IEM model, Object param, Object data);

    /**
     * 导出数据至Excel对象
     * 
     * @param model
     * @param param
     * @param data
     * @param workbook
     */
    abstract protected void export(IEM model, Object param, Object data, Workbook workbook);

    @Override
    public void exports(IEM model, Object param, Object data, OutputStream output) {
        Workbook workbook = this.newWorkbook(model, param, data);
        this.export(model, param, data, workbook);
        writeOutputStream(workbook, output);
    }

    private void writeOutputStream(Workbook workbook, OutputStream output) {
        if (null != workbook) {
            try {
                workbook.write(output);
            } catch (IOException e) {
                Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS02);
            } finally {
                if (workbook instanceof SXSSFWorkbook) {
                    ((SXSSFWorkbook) workbook).dispose();
                }
            }
        }
    }
}
