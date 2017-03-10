package com.forms.beneform4j.excel.core.model.loader.xml;

import java.util.HashMap;
import java.util.Map;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.core.model.loader.xml.bean.BeanEMExtractorParser;
import com.forms.beneform4j.excel.core.model.loader.xml.bean.BeanEMMatcherParser;
import com.forms.beneform4j.excel.core.model.loader.xml.bean.BeanEMValidatorParser;
import com.forms.beneform4j.excel.core.model.loader.xml.bean.BeanWorkbookParser;
import com.forms.beneform4j.excel.core.model.loader.xml.file.FileWorkbookGroupParser;
import com.forms.beneform4j.excel.core.model.loader.xml.file.FileWorkbookParser;
import com.forms.beneform4j.excel.core.model.loader.xml.text.TextWorkbookParser;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.ITreeEMComponentParser;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.ITreeEMDecorator;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.ITreeEMTdDecorator;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.TreeWorkbookParser;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.component.GridParser;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.component.NestedRegionParser;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : XML模型加载器的全局配置<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class XmlEMLoaderConfig {

    private static final Map<String, IEMTopElementParser> topElementParserMapping = new HashMap<String, IEMTopElementParser>();

    private static final Map<String, ITreeEMComponentParser> treeComponentParserMapping = new HashMap<String, ITreeEMComponentParser>();

    private static final Map<String, ITreeEMDecorator> decoratorMapping = new HashMap<String, ITreeEMDecorator>();

    private static final Map<String, ITreeEMTdDecorator> tdDecoratorMapping = new HashMap<String, ITreeEMTdDecorator>();

    static {
        // 注册一级子元素（主要是workbook元素）及其解析器
        registerTopElementParser(XmlEMLoaderConsts.TREE_WORKBOOK_ELEMENT_NAME, new TreeWorkbookParser());
        registerTopElementParser(XmlEMLoaderConsts.FILE_WORKBOOK_GROUP_ELEMENT_NAME, new FileWorkbookGroupParser());
        registerTopElementParser(XmlEMLoaderConsts.FILE_WORKBOOK_ELEMENT_NAME, new FileWorkbookParser());

        registerTopElementParser(XmlEMLoaderConsts.BEAN_WORKBOOK_ELEMENT_NAME, new BeanWorkbookParser());
        registerTopElementParser(XmlEMLoaderConsts.BEAN_WORKBOOK_EXTRACTOR_ELEMENT_NAME, new BeanEMExtractorParser());
        registerTopElementParser(XmlEMLoaderConsts.BEAN_WORKBOOK_MATCHER_ELEMENT_NAME, new BeanEMMatcherParser());
        registerTopElementParser(XmlEMLoaderConsts.BEAN_WORKBOOK_VALIDATOR_ELEMENT_NAME, new BeanEMValidatorParser());

        registerTopElementParser(XmlEMLoaderConsts.TEXT_WORKBOOK_ELEMENT_NAME, new TextWorkbookParser());

        // 注册树型模型的组件类型及其解析器
        registerTreeComponentParser(XmlEMLoaderConsts.GRID_COMPONENT_TYPE, new GridParser());
        registerTreeComponentParser(XmlEMLoaderConsts.NESTED_REGION_COMPONENT_TYPE, new NestedRegionParser());
    }

    /**
     * 根据树型Excel模型的组件类型获取相应的解析器
     * 
     * @param type
     * @return
     */
    public static ITreeEMComponentParser getTreeEMComponentParser(String type) {
        return treeComponentParserMapping.get(type);
    }

    /**
     * 注册树型Excel模型的组件类型及其相应的解析器
     * 
     * @param type
     * @param parser
     */
    public static void registerTreeComponentParser(String type, ITreeEMComponentParser parser) {
        treeComponentParserMapping.put(type, parser);
    }

    /**
     * 注入树型Excel模型的组件解析器
     * 
     * @param treeComponentParserMapping
     */
    public void setTreeComponentParserMapping(Map<String, ITreeEMComponentParser> treeComponentParserMapping) {
        if (null != treeComponentParserMapping) {
            XmlEMLoaderConfig.treeComponentParserMapping.putAll(treeComponentParserMapping);
        }
    }

    /**
     * 根据一级元素名称获取元素解析器
     * 
     * @param name
     * @return
     */
    public static IEMTopElementParser getEMTopElementParser(String name) {
        return topElementParserMapping.get(name);
    }

    /**
     * 根据一级元素名称获取元素解析器
     * 
     * @param namespace
     * @param name
     * @return
     */
    public static IEMTopElementParser getEMTopElementParser(String namespace, String name) {
        if (isDefaultNamespace(namespace)) {
            return topElementParserMapping.get(name);
        }
        return topElementParserMapping.get(namespace + ":" + name);
    }

    /**
     * 注册一级元素的名称及其相应的解析器
     * 
     * @param name
     * @param parser
     */
    public static void registerTopElementParser(String name, IEMTopElementParser parser) {
        topElementParserMapping.put(name, parser);
    }

    /**
     * 注册一级元素的名称及其相应的解析器
     * 
     * @param namespace
     * @param name
     * @param parser
     */
    public static void registerTopElementParser(String namespace, String name, IEMTopElementParser parser) {
        if (isDefaultNamespace(namespace)) {
            topElementParserMapping.put(name, parser);
        } else {
            topElementParserMapping.put(namespace + ":" + name, parser);
        }
    }

    /**
     * 注入一级元素的解析器
     * 
     * @param topElementParserMapping
     */
    public void setTopElementParserMapping(Map<String, IEMTopElementParser> topElementParserMapping) {
        if (null != topElementParserMapping) {
            XmlEMLoaderConfig.topElementParserMapping.putAll(topElementParserMapping);
        }
    }

    /**
     * 注册TreeEM元素的装饰器
     * 
     * @param namespace
     * @param name
     * @param decorator
     */
    public static void registerTreeEMDecorator(String namespace, String name, ITreeEMDecorator decorator) {
        if (isDefaultNamespace(namespace)) {
            decoratorMapping.put(name, decorator);
        } else {
            decoratorMapping.put(namespace + ":" + name, decorator);
        }
    }

    /**
     * 获取TreeEM元素的装饰器
     * 
     * @param namespace
     * @param name
     * @return
     */
    public static ITreeEMDecorator getTreeEMDecorator(String namespace, String name) {
        if (isDefaultNamespace(namespace)) {
            return decoratorMapping.get(name);
        }
        return decoratorMapping.get(namespace + ":" + name);
    }

    /**
     * 注入TreeEM元素的装饰器
     * 
     * @param treeEMDecoratorMapping
     */
    public void setTreeEMDecoratorMapping(Map<String, ITreeEMDecorator> treeEMDecoratorMapping) {
        if (null != treeEMDecoratorMapping) {
            XmlEMLoaderConfig.decoratorMapping.putAll(treeEMDecoratorMapping);
        }
    }

    /**
     * 注入Td元素的装饰器
     * 
     * @param treeEMTdDecoratorMapping
     */
    public void setTreeEMTdDecoratorMapping(Map<String, ITreeEMTdDecorator> treeEMTdDecoratorMapping) {
        if (null != treeEMTdDecoratorMapping) {
            XmlEMLoaderConfig.tdDecoratorMapping.putAll(treeEMTdDecoratorMapping);
        }
    }

    /**
     * 注册Td元素的装饰器
     * 
     * @param namespace
     * @param name
     * @param decorator
     */
    public static void registerTreeEMTdDecorator(String namespace, String name, ITreeEMTdDecorator decorator) {
        if (isDefaultNamespace(namespace)) {
            tdDecoratorMapping.put(name, decorator);
        } else {
            tdDecoratorMapping.put(namespace + ":" + name, decorator);
        }
    }

    /**
     * 获取Td元素的装饰器
     * 
     * @param namespace
     * @param name
     * @return
     */
    public static ITreeEMTdDecorator getTreeEMTdDecorator(String namespace, String name) {
        if (isDefaultNamespace(namespace)) {
            return tdDecoratorMapping.get(name);
        }
        return tdDecoratorMapping.get(namespace + ":" + name);
    }

    /**
     * 是否默认命名空间
     * 
     * @param namespace
     * @return
     */
    public static boolean isDefaultNamespace(String namespace) {
        return XmlEMLoaderConsts.DEFAULT_NAMESPACE.equalsIgnoreCase(namespace) || CoreUtils.isBlank(namespace);
    }
}
