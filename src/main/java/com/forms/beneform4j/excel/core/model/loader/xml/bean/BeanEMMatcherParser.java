package com.forms.beneform4j.excel.core.model.loader.xml.bean;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.xml.XmlHelper;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMMatcher;
import com.forms.beneform4j.excel.core.model.em.bean.impl.BeanEMManager;
import com.forms.beneform4j.excel.core.model.em.bean.impl.matcher.BaseBeanEMMatcher;
import com.forms.beneform4j.excel.core.model.em.bean.impl.matcher.MixinBeanEMMatcher;
import com.forms.beneform4j.excel.core.model.em.bean.impl.matcher.PositionBeanEMMatcher;
import com.forms.beneform4j.excel.core.model.em.bean.impl.matcher.SheetBeanEMMatcher;
import com.forms.beneform4j.excel.core.model.loader.IResourceEMLoadContext;
import com.forms.beneform4j.excel.core.model.loader.xml.IEMTopElementParser;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoaderConsts;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 解析XML配置中的匹配器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class BeanEMMatcherParser implements IEMTopElementParser {

    @Override
    public void parse(IResourceEMLoadContext context, Element element) {
        this.parse(element);
    }

    /* package */IBeanEMMatcher parse(Element ele) {
        IBeanEMMatcher matcher = parseMatcher(ele);
        String id = ele.getAttribute(XmlEMLoaderConsts.ID_PROPERTY);
        if (!CoreUtils.isBlank(id)) {
            BeanEMManager.registerMatcher(id, matcher);
        }
        return matcher;
    }

    /* package */IBeanEMMatcher parse(String ref) {
        IBeanEMMatcher parsed = BeanEMManager.getMatcher(ref);
        if (null != parsed) {
            return parsed;
        }
        MixinBeanEMMatcher matcher = new MixinBeanEMMatcher();
        matcher.setClassName(ref);
        return matcher;
    }

    private IBeanEMMatcher parseMatcher(Element element) {
        NodeList nl = element.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                String name = XmlHelper.getLocalName(ele);
                if ("position-matcher".equalsIgnoreCase(name)) {
                    return parsePositionMatcher(ele);
                } else if ("mixin-matcher".equalsIgnoreCase(name)) {
                    return parseMixinMatcher(ele);
                } else if ("base-matcher".equalsIgnoreCase(name)) {
                    return parseBaseMatcher(ele);
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private IBeanEMMatcher parseMixinMatcher(Element ele) {
        MixinBeanEMMatcher matcher = new MixinBeanEMMatcher();

        String beanName = ele.getAttribute("beanName");
        if (!CoreUtils.isBlank(beanName)) {
            matcher.setClassName(beanName);
        }

        String beanType = ele.getAttribute("beanType");
        if (!CoreUtils.isBlank(beanType)) {
            Class<? extends IBeanEMMatcher> cls = (Class<? extends IBeanEMMatcher>) CoreUtils.forName(beanType);
            matcher.setClassType(cls);
        }

        return matcher;
    }

    private IBeanEMMatcher parsePositionMatcher(Element ele) {
        PositionBeanEMMatcher matcher = new PositionBeanEMMatcher();

        String row = ele.getAttribute("row");
        if (!CoreUtils.isBlank(row)) {
            matcher.setRowPosition(Integer.parseInt(row));
        }

        String cell = ele.getAttribute("cell");
        if (!CoreUtils.isBlank(cell)) {
            matcher.setCellPosition(cell);
        }

        setSheetMatcherProperties(matcher, ele);
        return matcher;
    }

    private IBeanEMMatcher parseBaseMatcher(Element ele) {
        BaseBeanEMMatcher matcher = new BaseBeanEMMatcher();

        String offsetX = ele.getAttribute("offsetX");
        if (!CoreUtils.isBlank(offsetX)) {
            matcher.setOffsetX(Integer.parseInt(offsetX));
        }

        String offsetY = ele.getAttribute("offsetY");
        if (!CoreUtils.isBlank(offsetY)) {
            matcher.setOffsetY(Integer.parseInt(offsetY));
        }

        String isMergeCell = ele.getAttribute("isMergeCell");
        if (!CoreUtils.isBlank(isMergeCell)) {
            matcher.setMergeCell(CoreUtils.string2Boolean(isMergeCell));
        }

        String ignoreCase = ele.getAttribute("ignoreCase");
        if (!CoreUtils.isBlank(ignoreCase)) {
            matcher.setIgnoreCase(CoreUtils.string2Boolean(ignoreCase));
        }

        String value = ele.getAttribute("value");
        if (!CoreUtils.isBlank(value)) {
            matcher.setValue(value);
        }

        String pattern = ele.getAttribute("pattern");
        if (!CoreUtils.isBlank(pattern)) {
            matcher.setPattern(pattern);
        }

        setSheetMatcherProperties(matcher, ele);
        return matcher;
    }

    private void setSheetMatcherProperties(SheetBeanEMMatcher matcher, Element ele) {
        String sheetIndex = ele.getAttribute("sheetIndex");
        if (!CoreUtils.isBlank(sheetIndex)) {
            matcher.setSheetIndex(Integer.parseInt(sheetIndex));
        }

        String sheetName = ele.getAttribute("sheetName");
        if (!CoreUtils.isBlank(sheetName)) {
            matcher.setSheetName(sheetName);
        }
    }
}
