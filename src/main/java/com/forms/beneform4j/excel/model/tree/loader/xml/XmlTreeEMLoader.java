package com.forms.beneform4j.excel.model.tree.loader.xml;

import java.util.List;

import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.xml.XmlHelper;
import com.forms.beneform4j.core.util.xml.context.IXmlParserContext.XmlValidationMode;
import com.forms.beneform4j.core.util.xml.context.impl.XmlParserContext;
import com.forms.beneform4j.core.util.xml.parser.XmlParserUtils;
import com.forms.beneform4j.excel.model.base.IEM;
import com.forms.beneform4j.excel.model.base.loader.AbstractResourceEMLoader;

public class XmlTreeEMLoader extends AbstractResourceEMLoader {

    /**
     * 是否校验XML配置文件
     */
    private boolean validation;

    @Override
    protected void registerExcelModel(Resource resource, boolean force) {
        XmlParserContext context = new XmlParserContext();
        if (!isValidation()) {
            context.setXmlValidationMode(XmlValidationMode.NONE);
        } else {// 使用XSD校验XML配置文件
            context.setXmlValidationMode(XmlValidationMode.XSD);
        }
        Document document = XmlParserUtils.buildDocument(context, resource);
        List<Element> elements = XmlHelper.getChildElementsByTagName(document.getDocumentElement(), XmlTreeEMConsts.WORKBOOK);
        if (null != elements) {
            for (Element ele : elements) {
                IEM em = XmlTreeEMParserDelegate.parseWorkbookElement(ele);
                addEM(em, force);
            }
        }
    }

    public boolean isValidation() {
        return validation;
    }

    public void setValidation(boolean validation) {
        this.validation = validation;
    }
}
