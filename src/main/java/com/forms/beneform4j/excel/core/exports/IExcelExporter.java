package com.forms.beneform4j.excel.core.exports;

import java.io.OutputStream;

import com.forms.beneform4j.excel.core.data.loader.IDataLoader;
import com.forms.beneform4j.excel.core.model.em.IEM;

public interface IExcelExporter {

    /**
     * 导出数据至输出流
     * 
     * @param model
     * @param data
     * @param output
     */
    public void export(IEM model, Object data, OutputStream output);

    /**
     * 导出数据至模板文件，如果目标文件后缀和模板文件后缀不匹配，则替换成模板文件后缀
     * 
     * @param model
     * @param data
     * @param filename
     * @return
     */
    public String export(IEM model, Object data, String filename);

    public void export(IEM model, Object param, Object data, OutputStream output);

    public String export(IEM model, Object param, Object data, String filename);

    public void export(IEM model, Object param, IDataLoader loader, OutputStream output);

    public String export(IEM model, Object param, IDataLoader loader, String filename);
}
