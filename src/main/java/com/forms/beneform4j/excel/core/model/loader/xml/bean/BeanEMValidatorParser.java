package com.forms.beneform4j.excel.core.model.loader.xml.bean;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.xml.XmlHelper;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMValidator;
import com.forms.beneform4j.excel.core.model.em.bean.impl.BeanEMManager;
import com.forms.beneform4j.excel.core.model.em.bean.impl.validator.BaseBeanEMValidator;
import com.forms.beneform4j.excel.core.model.em.bean.impl.validator.MixinBeanEMValidator;
import com.forms.beneform4j.excel.core.model.loader.IResourceEMLoadContext;
import com.forms.beneform4j.excel.core.model.loader.xml.IEMTopElementParser;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoaderConsts;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 解析XML配置中的校验器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class BeanEMValidatorParser implements IEMTopElementParser {

    @Override
    public void parse(IResourceEMLoadContext context, Element element) {
        this.parse(element);

    }

    /* package */IBeanEMValidator parse(Element ele) {
        IBeanEMValidator validator = parseValidator(ele);
        String id = ele.getAttribute(XmlEMLoaderConsts.ID_PROPERTY);
        if (!CoreUtils.isBlank(id)) {
            BeanEMManager.registerValidator(id, validator);
        }
        return validator;
    }

    /* package */IBeanEMValidator parse(String ref) {
        IBeanEMValidator parsed = BeanEMManager.getValidator(ref);
        if (null != parsed) {
            return parsed;
        }
        MixinBeanEMValidator validator = new MixinBeanEMValidator();
        validator.setClassName(ref);
        return validator;
    }

    private IBeanEMValidator parseValidator(Element element) {
        NodeList nl = element.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                String name = XmlHelper.getLocalName(ele);
                if ("mixin-extractor".equalsIgnoreCase(name)) {
                    return parseMixinValidator(ele);
                } else if ("base-extractor".equalsIgnoreCase(name)) {
                    return parseBaseValidator(ele);
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private IBeanEMValidator parseMixinValidator(Element ele) {
        MixinBeanEMValidator extractor = new MixinBeanEMValidator();

        String beanName = ele.getAttribute("beanName");
        if (!CoreUtils.isBlank(beanName)) {
            extractor.setClassName(beanName);
        }

        String beanType = ele.getAttribute("beanType");
        if (!CoreUtils.isBlank(beanType)) {
            Class<? extends IBeanEMValidator> cls = (Class<? extends IBeanEMValidator>) CoreUtils.forName(beanType);
            extractor.setClassType(cls);
        }

        return extractor;
    }

    private IBeanEMValidator parseBaseValidator(Element ele) {
        BaseBeanEMValidator validator = new BaseBeanEMValidator();

        String offsetX = ele.getAttribute("offsetX");
        if (!CoreUtils.isBlank(offsetX)) {
            validator.setOffsetX(Integer.parseInt(offsetX));
        }

        String offsetY = ele.getAttribute("offsetY");
        if (!CoreUtils.isBlank(offsetY)) {
            validator.setOffsetY(Integer.parseInt(offsetY));
        }

        String allowEmpty = ele.getAttribute("allowEmpty");
        if (!CoreUtils.isBlank(allowEmpty)) {
            validator.setAllowEmpty(CoreUtils.string2Boolean(allowEmpty));
        }

        String ignoreCase = ele.getAttribute("ignoreCase");
        if (!CoreUtils.isBlank(ignoreCase)) {
            validator.setIgnoreCase(CoreUtils.string2Boolean(ignoreCase));
        }

        String value = ele.getAttribute("value");
        if (!CoreUtils.isBlank(value)) {
            validator.setValue(value);
        }

        String pattern = ele.getAttribute("pattern");
        if (!CoreUtils.isBlank(pattern)) {
            validator.setPattern(pattern);
        }

        return validator;
    }
}
