package com.forms.beneform4j.excel.core.model.loader.xml;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : XML模型加载器相关的常量<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class XmlEMLoaderConsts {

    // ===== 公共 =====
    /**
     * 命名空间
     */
    public static final String DEFAULT_NAMESPACE = "http://www.formssi.com/schema/beneform4j/excel";
    /**
     * 表示导入其它配置文件的元素
     */
    public static final String IMPORT_ELEMENT_NAME = "import";
    /**
     * 表示ID属性
     */
    public static final String ID_PROPERTY = "id";

    // ===== Excel文件模板 =====
    /**
     * 表示一个文件Excel模型的元素
     */
    public static final String FILE_WORKBOOK_ELEMENT_NAME = "file-workbook";
    /**
     * 表示一组文件Excel模型的元素
     */
    public static final String FILE_WORKBOOK_GROUP_ELEMENT_NAME = "file-workbook-group";

    // ===== 使用XML配置的树型模板 =====
    /**
     * 表示一个树型Excel模型的元素
     */
    public static final String TREE_WORKBOOK_ELEMENT_NAME = "tree-workbook";
    /**
     * 表示一个树型Excel模型的区域子元素
     */
    public static final String REGION_ELEMENT_NAME = "region";
    /**
     * 表示表格组件类型
     */
    public static final String GRID_COMPONENT_TYPE = "grid";
    /**
     * 表示嵌套组件类型
     */
    public static final String NESTED_REGION_COMPONENT_TYPE = "nested-region";

    // ===== 使用XML配置的Bean模型 =====
    /**
     * 表示一个Bean模型的元素
     */
    public static final String BEAN_WORKBOOK_ELEMENT_NAME = "bean-workbook";
    /**
     * Bean模型中的匹配器
     */
    public static final String BEAN_WORKBOOK_MATCHER_ELEMENT_NAME = "bean-workbook-matcher";
    /**
     * Bean模型中的校验器
     */
    public static final String BEAN_WORKBOOK_VALIDATOR_ELEMENT_NAME = "bean-workbook-validator";
    /**
     * Bean模型中的提取器
     */
    public static final String BEAN_WORKBOOK_EXTRACTOR_ELEMENT_NAME = "bean-workbook-extractor";

    // ===== 使用XML配置的Text模型 =====
    /**
     * 表示一个Text模型的元素
     */
    public static final String TEXT_WORKBOOK_ELEMENT_NAME = "text-workbook";
}
