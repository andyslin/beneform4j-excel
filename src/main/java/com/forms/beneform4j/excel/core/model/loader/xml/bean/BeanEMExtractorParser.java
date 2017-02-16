package com.forms.beneform4j.excel.core.model.loader.xml.bean;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.xml.XmlHelper;
import com.forms.beneform4j.excel.core.model.em.bean.BeanEMExtractResult.NextStep;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMExtractor;
import com.forms.beneform4j.excel.core.model.em.bean.impl.BeanEMManager;
import com.forms.beneform4j.excel.core.model.em.bean.impl.extractor.BaseBeanEMExtractor;
import com.forms.beneform4j.excel.core.model.em.bean.impl.extractor.MixinBeanEMExtractor;
import com.forms.beneform4j.excel.core.model.loader.IResourceEMLoadContext;
import com.forms.beneform4j.excel.core.model.loader.xml.IEMTopElementParser;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoaderConsts;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 解析XML配置中的提取器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class BeanEMExtractorParser implements IEMTopElementParser {

    @Override
    public void parse(IResourceEMLoadContext context, Element element) {
        this.parse(element);
    }

    /* package */IBeanEMExtractor parse(Element ele) {
        IBeanEMExtractor extractor = parseExtractor(ele);
        String id = ele.getAttribute(XmlEMLoaderConsts.ID_PROPERTY);
        if (!CoreUtils.isBlank(id)) {
            BeanEMManager.registerExtractor(id, extractor);
        }
        return extractor;
    }

    /* package */IBeanEMExtractor parse(String ref) {
        IBeanEMExtractor parsed = BeanEMManager.getExtractor(ref);
        if (null != parsed) {
            return parsed;
        }
        MixinBeanEMExtractor extractor = new MixinBeanEMExtractor();
        extractor.setClassName(ref);
        return extractor;
    }

    private IBeanEMExtractor parseExtractor(Element element) {
        NodeList nl = element.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                String name = XmlHelper.getLocalName(ele);
                if ("mixin-extractor".equalsIgnoreCase(name)) {
                    return parseMixinExtractor(ele);
                } else if ("base-extractor".equalsIgnoreCase(name)) {
                    return parseBaseExtractor(ele);
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private IBeanEMExtractor parseMixinExtractor(Element ele) {
        MixinBeanEMExtractor extractor = new MixinBeanEMExtractor();

        String beanName = ele.getAttribute("beanName");
        if (!CoreUtils.isBlank(beanName)) {
            extractor.setClassName(beanName);
        }

        String beanType = ele.getAttribute("beanType");
        if (!CoreUtils.isBlank(beanType)) {
            Class<? extends IBeanEMExtractor> cls = (Class<? extends IBeanEMExtractor>) CoreUtils.forName(beanType);
            extractor.setClassType(cls);
        }

        return extractor;
    }

    private IBeanEMExtractor parseBaseExtractor(Element ele) {
        BaseBeanEMExtractor extractor = new BaseBeanEMExtractor();

        String nextStep = ele.getAttribute("nextStep");
        if (!CoreUtils.isBlank(nextStep)) {
            extractor.setNextStep(NextStep.valueOf(nextStep));
        }

        String offsetSheet = ele.getAttribute("offsetSheet");
        if (!CoreUtils.isBlank(offsetSheet)) {
            extractor.setOffsetSheet(Integer.parseInt(offsetSheet));
        }

        String offsetX = ele.getAttribute("offsetX");
        if (!CoreUtils.isBlank(offsetX)) {
            extractor.setOffsetX(Integer.parseInt(offsetX));
        }

        String offsetY = ele.getAttribute("offsetY");
        if (!CoreUtils.isBlank(offsetY)) {
            extractor.setOffsetY(Integer.parseInt(offsetY));
        }

        String skipOffsetSheet = ele.getAttribute("skipOffsetSheet");
        if (!CoreUtils.isBlank(skipOffsetSheet)) {
            extractor.setSkipOffsetSheet(Integer.parseInt(skipOffsetSheet));
        }

        String skipOffsetX = ele.getAttribute("skipOffsetX");
        if (!CoreUtils.isBlank(skipOffsetX)) {
            extractor.setSkipOffsetX(Integer.parseInt(skipOffsetX));
        }

        String skipOffsetY = ele.getAttribute("skipOffsetY");
        if (!CoreUtils.isBlank(skipOffsetY)) {
            extractor.setSkipOffsetY(Integer.parseInt(skipOffsetY));
        }

        return extractor;
    }
}
