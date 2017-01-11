package com.forms.beneform4j.excel.exports.base;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.forms.beneform4j.excel.model.base.IEM;

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
    public void export(IEM model, Object param, Object data, OutputStream output) {
        Workbook workbook = this.newWorkbook(model, param, data);
        this.export(model, param, data, workbook);
        writeOutputStream(workbook, output);
    }

    private void writeOutputStream(Workbook workbook, OutputStream output) {
        if (null != workbook) {
            try {
                workbook.write(output);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (workbook instanceof SXSSFWorkbook) {
                    ((SXSSFWorkbook) workbook).dispose();
                }
            }
        }
    }
}
