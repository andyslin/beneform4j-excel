package com.forms.beneform4j.excel.core.exports.file;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 使用Excel文件作为模板的Excel导出实现中的代理接口<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public interface IExcelExporterDelegate {

    /**
     * 导出至workbook对象
     * 
     * @param param
     * @param data
     * @param workbook
     * @return
     * @throws Exception
     */
    boolean export(Object param, Object data, Workbook workbook) throws Exception;

}
