package com.forms.beneform4j.excel.core.imports;

import java.io.InputStream;

import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.core.imports.stream.IWorkbookStreamHandler;
import com.forms.beneform4j.excel.core.model.em.IEM;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Excel导入静态帮助类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class ExcelImporters {

    /**
     * 导入Excel文件，并调用回调处理器处理
     * 
     * @param input Excel文件输入流
     * @param handler Excel文件回调处理器
     */
    public static void imports(InputStream input, IWorkbookStreamHandler handler) {
        IExcelImporter importer = getImporter();
        importer.imports(input, handler);
    }

    /**
     * 导入Excel文件，并调用回调处理器处理
     * 
     * @param location Excel资源位置
     * @param handler Excel文件回调处理器
     */
    public static void imports(String location, IWorkbookStreamHandler handler) {
        IExcelImporter importer = getImporter();
        importer.imports(location, handler);
    }

    /**
     * 导入Excel文件至JavaBean中
     * 
     * @param input Excel文件输入流
     * @param cls JavaBean的类型，根据cls从配置或注解中加载模型
     * @return JavaBean实例
     */
    public static <T> T imports(InputStream input, Class<T> cls) {
        IExcelImporter importer = getImporter();
        return importer.imports(input, cls);
    }

    /**
     * 导入Excel文件至JavaBean中
     * 
     * @param location Excel资源位置
     * @param cls JavaBean的类型，根据cls从配置或注解中加载模型
     * @return JavaBean实例
     */
    public static <T> T imports(String location, Class<T> cls) {
        IExcelImporter importer = getImporter();
        return importer.imports(location, cls);
    }

    /**
     * 导入Excel文件
     * 
     * @param input Excel文件输入流
     * @param modelId 模型ID
     * @return 如果modelId对应的模型为IBeanEM，则返回JavaBean，如果为ITextEM或ITreeEM，则返回转换成文本文件后的文件名
     */
    public static Object imports(InputStream input, String modelId) {
        IExcelImporter importer = getImporter();
        return importer.imports(input, modelId);
    }

    /**
     * 导入Excel文件
     * 
     * @param location Excel资源位置
     * @param modelId 模型ID
     * @return 如果modelId对应的模型为IBeanEM，则返回JavaBean，如果为ITextEM或ITreeEM，则返回转换成文本文件后的文件名
     */
    public static Object imports(String location, String modelId) {
        IExcelImporter importer = getImporter();
        return importer.imports(location, modelId);
    }

    /**
     * 导入Excel文件
     * 
     * @param input Excel文件输入流
     * @param modelId 模型ID
     * @param cls JavaBean的类型
     * @return JavaBean实例，如果modelId对应的模型不是IBeanEM，并且cls不是字符串，则抛出异常
     */
    public static <T> T imports(InputStream input, String modelId, Class<T> cls) {
        IExcelImporter importer = getImporter();
        return importer.imports(input, modelId, cls);
    }

    /**
     * 导入Excel文件
     * 
     * @param location Excel资源位置
     * @param modelId 模型ID
     * @param cls JavaBean的类型
     * @return JavaBean实例，如果modelId对应的模型不是IBeanEM，并且cls不是字符串，则抛出异常
     */
    public static <T> T imports(String location, String modelId, Class<T> cls) {
        IExcelImporter importer = getImporter();
        return importer.imports(location, modelId, cls);
    }

    /**
     * 导入Excel文件
     * 
     * @param input Excel文件输入流
     * @param model 模型
     * @return 如果model为IBeanEM，则返回JavaBean，如果为ITextEM或ITreeEM，则返回转换成文本文件后的文件名
     */
    public static Object imports(InputStream input, IEM model) {
        IExcelImporter importer = getImporter();
        return importer.imports(input, model);
    }

    /**
     * 导入Excel文件
     * 
     * @param location Excel资源位置
     * @param model 模型
     * @return 如果model为IBeanEM，则返回JavaBean，如果为ITextEM或ITreeEM，则返回转换成文本文件后的文件名
     */
    public static Object imports(String location, IEM model) {
        IExcelImporter importer = getImporter();
        return importer.imports(location, model);
    }

    private static IExcelImporter getImporter() {
        return ExcelComponentConfig.getExcelImporter();
    }
}
