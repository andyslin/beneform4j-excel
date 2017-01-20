package com.forms.beneform4j.excel.core.imports;

import java.io.InputStream;

import com.forms.beneform4j.excel.core.imports.stream.IWorkbookStreamHandler;
import com.forms.beneform4j.excel.core.model.em.IEM;

public interface IExcelImporter {

    /**
     * 导入Excel文件，并调用回调处理器处理
     * 
     * @param input Excel文件输入流
     * @param handler Excel文件回调处理器
     */
    public void imports(InputStream input, IWorkbookStreamHandler handler);

    /**
     * 导入Excel文件，并调用回调处理器处理
     * 
     * @param location Excel资源位置
     * @param handler Excel文件回调处理器
     */
    public void imports(String location, IWorkbookStreamHandler handler);

    /**
     * 导入Excel文件至JavaBean中
     * 
     * @param input Excel文件输入流
     * @param cls JavaBean的类型
     * @return JavaBean实例
     */
    public <T> T imports(InputStream input, Class<T> cls);

    /**
     * 导入Excel文件至JavaBean中
     * 
     * @param location Excel资源位置
     * @param cls JavaBean的类型
     * @return JavaBean实例
     */
    public <T> T imports(String location, Class<T> cls);

    /**
     * 导入Excel文件至JavaBean中
     * 
     * @param input Excel文件输入流
     * @param cls JavaBean的类型
     * @return JavaBean实例
     */
    public Object imports(InputStream input, String modelId);

    /**
     * 导入Excel文件至JavaBean中
     * 
     * @param location Excel资源位置
     * @param cls JavaBean的类型
     * @return JavaBean实例
     */
    public Object imports(String location, String modelId);

    /**
     * 导入Excel文件至JavaBean中
     * 
     * @param input Excel文件输入流
     * @param cls JavaBean的类型
     * @return JavaBean实例
     */
    public <T> T imports(InputStream input, String modelId, Class<T> cls);

    /**
     * 导入Excel文件至JavaBean中
     * 
     * @param location Excel资源位置
     * @param cls JavaBean的类型
     * @return JavaBean实例
     */
    public <T> T imports(String location, String modelId, Class<T> cls);

    /**
     * 导入Excel文件至JavaBean中
     * 
     * @param input Excel文件输入流
     * @param cls JavaBean的类型
     * @return JavaBean实例
     */
    public Object imports(InputStream input, IEM model);

    /**
     * 导入Excel文件至JavaBean中
     * 
     * @param location Excel资源位置
     * @param cls JavaBean的类型
     * @return JavaBean实例
     */
    public Object imports(String location, IEM model);

}
