package com.forms.beneform4j.excel.core.model.loader.xml;

import java.util.HashMap;
import java.util.Map;

import com.forms.beneform4j.excel.core.model.loader.xml.bean.BeanEMExtractorParser;
import com.forms.beneform4j.excel.core.model.loader.xml.bean.BeanEMMatcherParser;
import com.forms.beneform4j.excel.core.model.loader.xml.bean.BeanEMValidatorParser;
import com.forms.beneform4j.excel.core.model.loader.xml.bean.BeanWorkbookParser;
import com.forms.beneform4j.excel.core.model.loader.xml.file.FileWorkbookGroupParser;
import com.forms.beneform4j.excel.core.model.loader.xml.file.FileWorkbookParser;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.ITreeEMComponentParser;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.TreeWorkbookParser;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.component.GridParser;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.component.NestedRegionParser;

public class XmlEMLoaderConfig {

    private static final Map<String, IEMTopElementParser> topElementParserMapping = new HashMap<String, IEMTopElementParser>();

    private static final Map<String, ITreeEMComponentParser> treeComponentParserMapping = new HashMap<String, ITreeEMComponentParser>();

    static {
        // 注册一级子元素（主要是workbook元素）及其解析器
        registerTopElementParser(XmlEMLoaderConsts.TREE_WORKBOOK_ELEMENT_NAME, new TreeWorkbookParser());
        registerTopElementParser(XmlEMLoaderConsts.FILE_WORKBOOK_GROUP_ELEMENT_NAME, new FileWorkbookGroupParser());
        registerTopElementParser(XmlEMLoaderConsts.FILE_WORKBOOK_ELEMENT_NAME, new FileWorkbookParser());

        registerTopElementParser(XmlEMLoaderConsts.BEAN_WORKBOOK_ELEMENT_NAME, new BeanWorkbookParser());
        registerTopElementParser(XmlEMLoaderConsts.BEAN_WORKBOOK_EXTRACTOR_ELEMENT_NAME, new BeanEMExtractorParser());
        registerTopElementParser(XmlEMLoaderConsts.BEAN_WORKBOOK_MATCHER_ELEMENT_NAME, new BeanEMMatcherParser());
        registerTopElementParser(XmlEMLoaderConsts.BEAN_WORKBOOK_VALIDATOR_ELEMENT_NAME, new BeanEMValidatorParser());

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
     * 根据元素名称获取Excel模型元素解析器
     * 
     * @param name
     * @return
     */
    public static IEMTopElementParser getEMTopElementParser(String name) {
        return topElementParserMapping.get(name);
    }

    /**
     * 注册Excel模型元素的名称及其相应的解析器
     * 
     * @param name
     * @param parser
     */
    public static void registerTopElementParser(String name, IEMTopElementParser parser) {
        topElementParserMapping.put(name, parser);
    }

    /**
     * 注入Excel模型元素的解析器
     * 
     * @param topElementParserMapping
     */
    public void setTopElementParserMapping(Map<String, IEMTopElementParser> topElementParserMapping) {
        if (null != topElementParserMapping) {
            XmlEMLoaderConfig.topElementParserMapping.putAll(topElementParserMapping);
        }
    }
}
