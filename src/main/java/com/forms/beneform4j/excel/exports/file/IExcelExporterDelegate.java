package com.forms.beneform4j.excel.exports.file;

import org.apache.poi.ss.usermodel.Workbook;

public interface IExcelExporterDelegate {

    boolean export(Object param, Object data, Workbook workbook) throws Exception;

}
