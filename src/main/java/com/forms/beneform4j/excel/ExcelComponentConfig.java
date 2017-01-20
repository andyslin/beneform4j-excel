package com.forms.beneform4j.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forms.beneform4j.core.util.config.ConfigHelper;
import com.forms.beneform4j.excel.core.exports.IExcelExporter;
import com.forms.beneform4j.excel.core.exports.tree.painter.ITreeEMComponentXlsxPainter;
import com.forms.beneform4j.excel.core.exports.tree.painter.impl.GridXlsxPainter;
import com.forms.beneform4j.excel.core.exports.tree.painter.impl.NestedRegionXlsxPainter;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.NestedRegionTreeEMComponent;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Grid;
import com.forms.beneform4j.excel.core.model.loader.IEMLoader;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoaderConfig;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Excel组件配置<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2016-12-22<br>
 */
public class ExcelComponentConfig {

    private static final Map<Class<? extends ITreeEMComponent>, ITreeEMComponentXlsxPainter> xlsxPainters = new HashMap<Class<? extends ITreeEMComponent>, ITreeEMComponentXlsxPainter>();

    static {
        // 注册树型模型的组件类型及其Xlsx渲染器
        registerXlsxPainter(Grid.class, new GridXlsxPainter());
        registerXlsxPainter(NestedRegionTreeEMComponent.class, new NestedRegionXlsxPainter());
    }

    /**
     * Excel模型的加载器列表
     */
    private static List<IEMLoader> emLoaders;

    /**
     * 参数对象变量名称
     */
    private static String paramObjectVarName;

    /**
     * 数据对象变量名称
     */
    private static String dataObjectVarName;

    /**
     * Excel导出器
     */
    private static IExcelExporter excelExporter;

    /**
     * 获取模型加载器列表
     * 
     * @return
     */
    public static List<IEMLoader> getEmLoaders() {
        return emLoaders;
    }

    /**
     * 注入模型加载器类别
     * 
     * @param emLoaders
     */
    public void setEmLoaders(List<IEMLoader> emLoaders) {
        ExcelComponentConfig.emLoaders = emLoaders;
    }

    /**
     * 获取Xlsx组件绘制器
     * 
     * @param component
     * @return
     */
    public static ITreeEMComponentXlsxPainter getXlsxPainter(ITreeEMComponent component) {
        if (null == component) {
            return null;
        }
        return xlsxPainters.get(component.getClass());
    }

    /**
     * 注册Xlsx组件绘制器
     * 
     * @param cls
     * @param painter
     */
    public static void registerXlsxPainter(Class<? extends ITreeEMComponent> cls, ITreeEMComponentXlsxPainter painter) {
        xlsxPainters.put(cls, painter);
    }

    /**
     * 注入Xlsx组件绘制器
     * 
     * @param xlsxPainters
     */
    public void setXlsxPainters(Map<Class<? extends ITreeEMComponent>, ITreeEMComponentXlsxPainter> xlsxPainters) {
        if (null != xlsxPainters) {
            ExcelComponentConfig.xlsxPainters.putAll(xlsxPainters);
        }
    }

    /**
     * 获取参数对象变量名称
     * 
     * @return
     */
    public static String getParamObjectVarName() {
        return ConfigHelper.getValue(paramObjectVarName, "param_object_var_name");
    }

    /**
     * 注入参数对象变量名称
     * 
     * @param paramObjectVarName
     */
    public void setParamObjectVarName(String paramObjectVarName) {
        ExcelComponentConfig.paramObjectVarName = paramObjectVarName;
    }

    /**
     * 获取数据对象变量名称
     * 
     * @return
     */
    public static String getDataObjectVarName() {
        return ConfigHelper.getValue(dataObjectVarName, "data_object_var_name");
    }

    /**
     * 注入数据对象变量名称
     * 
     * @param dataObjectVarName
     */
    public void setDataObjectVarName(String dataObjectVarName) {
        ExcelComponentConfig.dataObjectVarName = dataObjectVarName;
    }

    /**
     * 获取Excel导出器
     * 
     * @return
     */
    public static IExcelExporter getExcelExporter() {
        return ConfigHelper.getComponent(excelExporter, IExcelExporter.class);
    }

    /**
     * 注入Excel导出器
     * 
     * @param excelExporter
     */
    public void setExcelExporter(IExcelExporter excelExporter) {
        ExcelComponentConfig.excelExporter = excelExporter;
    }

    /**
     * 注入XmlEMLoaderConfig配置，由于XmlEMLoaderConfig中都是静态配置，所以这里只需要提供注入的方法即可，而不需要实际的赋值
     * 
     * @param xmlEMLoaderConfig
     */
    public void setXmlEMLoaderConfig(XmlEMLoaderConfig xmlEMLoaderConfig) {}
}
