package com.forms.beneform4j.excel.core.model.loader.xml;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.forms.beneform4j.core.util.config.BaseConfig;
import com.forms.beneform4j.core.util.logger.CommonLogger;
import com.forms.beneform4j.core.util.xml.XmlHelper;
import com.forms.beneform4j.core.util.xml.context.IXmlParserContext.XmlValidationMode;
import com.forms.beneform4j.core.util.xml.context.impl.XmlParserContext;
import com.forms.beneform4j.core.util.xml.parser.XmlParserUtils;
import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.core.model.loader.IResourceEMLoadContext;
import com.forms.beneform4j.excel.core.model.loader.base.AbstractResourceEMLoader;
import com.forms.beneform4j.excel.core.model.loader.xml.workbook.IEMWorkbookParser;

public class XmlEMLoader extends AbstractResourceEMLoader {

    /**
     * 表示Excel模型ID属性
     */
    public static final String WORKBOOK_ID_PROPERTY = "id";

    /**
     * 表示一个树型Excel模型的区域子元素
     */
    public static final String REGION_ELEMENT_NAME = "region";
    /**
     * 表示一个树型Excel模型的元素
     */
    public static final String TREE_WORKBOOK_ELEMENT_NAME = "tree-workbook";
    /**
     * 表示一个文件Excel模型的元素
     */
    public static final String FILE_WORKBOOK_ELEMENT_NAME = "file-workbook";
    /**
     * 表示一组文件Excel模型的元素
     */
    public static final String FILE_WORKBOOK_GROUP_ELEMENT_NAME = "file-workbook-group";
    /**
     * 表示导入其它配置文件的元素
     */
    public static final String IMPORT_ELEMENT_NAME = "import";

    /**
     * 表示表格组件类型
     */
    public static final String GRID_COMPONENT_TYPE = "grid";
    /**
     * 表示嵌套组件类型
     */
    public static final String NESTED_REGION_COMPONENT_TYPE = "nested-region";

    /**
     * 是否校验XML配置文件
     */
    private boolean validation;

    @Override
    protected void loadResourceEM(IResourceEMLoadContext context) {
        XmlParserContext xpc = new XmlParserContext();
        if (!isValidation()) {
            xpc.setXmlValidationMode(XmlValidationMode.NONE);
        } else {// 使用XSD校验XML配置文件
            xpc.setXmlValidationMode(XmlValidationMode.XSD);
        }
        CommonLogger.info("Loading IEM form " + context.getResource());
        Document document = XmlParserUtils.buildDocument(xpc, context.getResource());
        NodeList nl = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                if (IMPORT_ELEMENT_NAME.equalsIgnoreCase(XmlHelper.getLocalName(ele))) {
                    this.loadImportResourceEM(context, ele);
                } else {
                    IEMWorkbookParser parser = getWorkbookParser(ele);
                    if (null != parser) {
                        parser.parse(context, ele);
                    } else {
                        CommonLogger.warn("未找到" + ele + "元素的解析器，忽略该配置");
                    }
                }
            }
        }
    }

    /**
     * 获取workbook级元素的解析器
     * 
     * @param element
     * @return
     */
    protected IEMWorkbookParser getWorkbookParser(Element element) {
        String name = XmlHelper.getLocalName(element);
        return ExcelComponentConfig.getEMWorkbookParser(name);
    }

    private void loadImportResourceEM(IResourceEMLoadContext context, Element ele) {
        String location = ele.getAttribute("resource");

        Resource importResource = null;
        boolean absoluteLocation = false;
        try {
            absoluteLocation = ResourcePatternUtils.isUrl(location) || ResourceUtils.toURI(location).isAbsolute();
        } catch (URISyntaxException ex) {
            // cannot convert to an URI, considering the location relative
            // unless it is the well-known Spring prefix "classpath*:"
        }

        // Absolute or relative?
        if (absoluteLocation) {
            importResource = BaseConfig.getResourcePatternResolver().getResource(location);
        } else {
            // No URL -> considering resource location as relative to the current file.
            try {
                importResource = context.getResource().createRelative(location);
                if (!importResource.exists()) {
                    String baseLocation = context.getResource().getURL().toString();
                    String applyRelativePath = StringUtils.applyRelativePath(baseLocation, location);
                    importResource = BaseConfig.getResourcePatternResolver().getResource(applyRelativePath);
                }
            } catch (IOException ex) {
            }
        }

        if (null != importResource && importResource.exists()) {
            this.loadResourceEM(convertResourceEMLoadContext(context, importResource));
        }
    }

    public boolean isValidation() {
        return validation;
    }

    public void setValidation(boolean validation) {
        this.validation = validation;
    }
}
