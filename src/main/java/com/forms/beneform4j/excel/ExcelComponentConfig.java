package com.forms.beneform4j.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forms.beneform4j.core.util.config.ConfigHelper;
import com.forms.beneform4j.excel.data.accessor.IDataAccessorFactory;
<<<<<<< HEAD
import com.forms.beneform4j.excel.exports.IExcelExporter;
=======
>>>>>>> branch 'master' of http://192.168.22.190:8090/beneform4j/beneform4j-excel.git
import com.forms.beneform4j.excel.exports.tree.painter.ITreeEMComponentXlsxPainter;
import com.forms.beneform4j.excel.exports.tree.painter.impl.GridXlsxPainter;
import com.forms.beneform4j.excel.exports.tree.painter.impl.NestedRegionXlsxPainter;
import com.forms.beneform4j.excel.model.base.IEMLoader;
import com.forms.beneform4j.excel.model.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.model.tree.component.NestedRegionTreeEMComponent;
import com.forms.beneform4j.excel.model.tree.component.grid.Grid;
import com.forms.beneform4j.excel.model.tree.loader.xml.ITreeEMComponentParser;
import com.forms.beneform4j.excel.model.tree.loader.xml.XmlTreeEMConsts;
import com.forms.beneform4j.excel.model.tree.loader.xml.impl.GridParser;
import com.forms.beneform4j.excel.model.tree.loader.xml.impl.NestedRegionParser;

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

    /**
     * 使用XML配置Excel 树型模型时的组件类型及其解析器的映射关系
     */
    private static final Map<String, ITreeEMComponentParser> componentParserMapping = new HashMap<String, ITreeEMComponentParser>();

    private static final Map<Class<? extends ITreeEMComponent>, ITreeEMComponentXlsxPainter> xlsxPainters = new HashMap<Class<? extends ITreeEMComponent>, ITreeEMComponentXlsxPainter>();

    static {
        registerComponentParser(XmlTreeEMConsts.GRID_COMPONENT, new GridParser());
        registerComponentParser(XmlTreeEMConsts.NESTED_REGION_COMPONENT, new NestedRegionParser());

        registerXlsxPainter(Grid.class, new GridXlsxPainter());
        registerXlsxPainter(NestedRegionTreeEMComponent.class, new NestedRegionXlsxPainter());
    }

    /**
     * Excel模型的加载器列表
     */
    private static List<IEMLoader> emLoaders;

    /**
     * 数据提供者工厂
     */
    private static IDataAccessorFactory dataAccessorFactory;

    /**
     * 参数对象变量名称
     */
    private static String paramObjectVarName;

    /**
<<<<<<< HEAD
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
     * 根据组件类型获取解析器
     * 
     * @param type
     * @return
     */
    public static ITreeEMComponentParser getTreeEMComponentParser(String type) {
        return componentParserMapping.get(type);
    }

    /**
     * 注册组件类型及其相应的解析器
     * 
     * @param type
     * @param parser
     */
    public static void registerComponentParser(String type, ITreeEMComponentParser parser) {
        componentParserMapping.put(type, parser);
    }

    /**
     * 注入组件解析器
     * 
     * @param componentParserMapping
     */
    public void setComponentParserMapping(Map<String, ITreeEMComponentParser> componentParserMapping) {
        if (null != componentParserMapping) {
            ExcelComponentConfig.componentParserMapping.putAll(componentParserMapping);
        }
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
     * 获取数据访问器工厂
     * 
     * @return
     */
    public static IDataAccessorFactory getDataAccessorFactory() {
        return ConfigHelper.getComponent(dataAccessorFactory, IDataAccessorFactory.class);
    }

    /**
     * 注入数据访问器工厂
     * 
     * @param dataAccessorFactory
     */
    public void setDataAccessorFactory(IDataAccessorFactory dataAccessorFactory) {
        ExcelComponentConfig.dataAccessorFactory = dataAccessorFactory;
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
=======
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
     * 根据组件类型获取解析器
     * 
     * @param type
     * @return
     */
    public static ITreeEMComponentParser getTreeEMComponentParser(String type) {
        return componentParserMapping.get(type);
    }

    /**
     * 注册组件类型及其相应的解析器
     * 
     * @param type
     * @param parser
     */
    public static void registerComponentParser(String type, ITreeEMComponentParser parser) {
        componentParserMapping.put(type, parser);
    }

    /**
     * 注入组件解析器
     * 
     * @param componentParserMapping
     */
    public void setComponentParserMapping(Map<String, ITreeEMComponentParser> componentParserMapping) {
        if (null != componentParserMapping) {
            ExcelComponentConfig.componentParserMapping.putAll(componentParserMapping);
        }
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
     * 获取数据访问器工厂
     * 
     * @return
     */
    public static IDataAccessorFactory getDataAccessorFactory() {
        return ConfigHelper.getComponent(dataAccessorFactory, IDataAccessorFactory.class);
    }

    /**
     * 注入数据访问器工厂
     * 
     * @param dataAccessorFactory
     */
    public void setDataAccessorFactory(IDataAccessorFactory dataAccessorFactory) {
        ExcelComponentConfig.dataAccessorFactory = dataAccessorFactory;
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
>>>>>>> branch 'master' of http://192.168.22.190:8090/beneform4j/beneform4j-excel.git
    }
}
