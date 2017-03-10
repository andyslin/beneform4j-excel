package com.forms.beneform4j.excel.core.model.loader.xml.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.core.util.xml.XmlHelper;
import com.forms.beneform4j.excel.core.model.em.text.impl.TextEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMRegion.OffsetPoint;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMSheet;
import com.forms.beneform4j.excel.core.model.em.tree.impl.TreeEM;
import com.forms.beneform4j.excel.core.model.em.tree.impl.TreeEMRegion;
import com.forms.beneform4j.excel.core.model.em.tree.impl.TreeEMSheet;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoaderConfig;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoaderConsts;
import com.forms.beneform4j.excel.core.model.loader.xml.text.TextWorkbookParser;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 树型XML配置解析代理<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class TreeWorkbookParserDelegate {

    /**
     * 表示一个表单配置的元素
     */
    private static final String SHEET = "sheet";

    private static final String WORKBOOK_NAME = "name";
    private static final String WORKBOOK_DESC = "desc";
    private static final String WORKBOOK_PRIOR = "prior";
    private static final String TEXT_WORKBOOK_REF = "textWorkbook-ref";

    private static final String SHEET_NAME = "name";
    private static final String SHEET_CONDITION = "condition";
    private static final String SHEET_EXPRESSION = "expression";

    private static final String REGION_NAME = "name";
    private static final String REGION_TITLE = "title";
    private static final String REGION_CONDITION = "condition";
    private static final String REGION_EXPRESSION = "expression";
    private static final String REGION_OFFSET_NAME = "offsetName";
    private static final String REGION_OFFSET_POINT = "offsetPoint";
    private static final String REGION_OFFSET_X = "offsetX";
    private static final String REGION_OFFSET_Y = "offsetY";
    private static final String REGION_TYPE = "type";

    /**
     * 解析一个workbook文档
     * 
     * @param modelId
     * @param document
     * @return
     */
    public static TreeEM parseWorkbookDocument(String modelId, Document document) {
        Element element = document.getDocumentElement();
        String name = XmlHelper.getLocalName(element);
        Element workbook = element;
        if (!XmlEMLoaderConsts.TREE_WORKBOOK_ELEMENT_NAME.equalsIgnoreCase(name)) {
            workbook = XmlHelper.getChildElementByTagName(element, XmlEMLoaderConsts.TREE_WORKBOOK_ELEMENT_NAME);
        }
        if (null != workbook) {
            return parseWorkbookElement(modelId, workbook);
        } else {
            return null;
        }
    }

    /**
     * 解析一个workbook元素
     * 
     * @param modelId
     * @param workbook
     * @return
     */
    public static TreeEM parseWorkbookElement(String modelId, Element workbook) {
        TreeEM em = parseWorkbookElement0(modelId, workbook);
        if (null != em && CoreUtils.isBlank(em.getTextWorkbookRef())) {
            Element ele = XmlHelper.getChildElementByTagName(workbook, XmlEMLoaderConsts.TEXT_WORKBOOK_ELEMENT_NAME);
            if (null != ele) {
                TextWorkbookParser parser = new TextWorkbookParser();
                TextEM textEM = parser.parseTextEM(ele);
                em.setTextWorkbook(textEM);
            }
        }
        doDecorate(workbook, em);
        return em;
    }

    protected static void doDecorate(Element workbook, TreeEM em) {
        NodeList nl = workbook.getChildNodes();
        for (int i = 0, l = nl.getLength(); i < l; i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                ITreeEMDecorator decorator = getDecorator((Element) node);
                if (null != decorator) {
                    decorator.decorate(em, workbook, (Element) node);
                }
            }
        }
    }

    /**
     * 解析一个workbook元素
     * 
     * @param modelId
     * @param workbook
     * @return
     */
    private static TreeEM parseWorkbookElement0(String modelId, Element workbook) {
        // sheet配置
        List<Element> sheets = XmlHelper.getChildElementsByTagName(workbook, SHEET);
        if (!sheets.isEmpty()) {// 存在<sheet>元素
            return buildTreeFormSheets(modelId, workbook, sheets);
        }

        List<Element> regions = XmlHelper.getChildElementsByTagName(workbook, XmlEMLoaderConsts.REGION_ELEMENT_NAME);
        if (!regions.isEmpty()) {// 不存在<sheet>，但存在<region>元素，视作只有一个<sheet>
            return buildTreeFormRegions(modelId, workbook, regions);
        }

        // 即不存在<sheet>，也不存在<region>，视作gird模型
        ITreeEMComponentParser parser = XmlEMLoaderConfig.getTreeEMComponentParser(XmlEMLoaderConsts.GRID_COMPONENT_TYPE);
        if (null != parser) {
            return buildTreeFormComponent(modelId, workbook, parser);
        } else {
            return null;
        }
    }

    /**
     * 解析区域元素
     * 
     * @param modelId
     * @param region
     * @return
     */
    public static TreeEMRegion parseRegionElement(String modelId, Element region) {
        String type = getParserType(region);
        ITreeEMComponentParser parser = XmlEMLoaderConfig.getTreeEMComponentParser(type);
        if (null == parser) {
            Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS26, type);
        }
        ITreeEMComponent component = parser.parse(modelId, region);
        if (null != component) {
            TreeEMRegion emRegion = new TreeEMRegion();
            component.setRegion(emRegion);
            emRegion.setComponent(component);

            setRegionProperties(region, emRegion);
            return emRegion;
        }
        return null;
    }

    /**
     * 根据sheet构建Excel模型
     * 
     * @param modelId
     * @param workbook
     * @param sheets
     * @return
     */
    private static TreeEM buildTreeFormSheets(String modelId, Element workbook, List<Element> sheets) {
        TreeEM em = new TreeEM();
        List<ITreeEMSheet> emSheets = new ArrayList<ITreeEMSheet>();
        for (Element sheet : sheets) {
            TreeEMSheet emSheet = parseSheetElement(modelId, sheet);
            if (null != emSheet) {
                emSheet.setWorkbook(em);
                emSheets.add(emSheet);
            }
        }
        if (!emSheets.isEmpty()) {
            em.setSheets(emSheets);
            setWorkbookProperties(modelId, workbook, em);
            return em;
        }
        return null;
    }

    /**
     * 根据regions构建Excel模型
     * 
     * @param modelId
     * @param workbook
     * @param regions
     * @return
     */
    private static TreeEM buildTreeFormRegions(String modelId, Element workbook, List<Element> regions) {
        TreeEMSheet emSheet = new TreeEMSheet();
        List<ITreeEMRegion> emRegions = new ArrayList<ITreeEMRegion>();
        for (Element region : regions) {
            TreeEMRegion emRegion = parseRegionElement(modelId, region);
            if (null != emRegion) {
                emRegion.setSheet(emSheet);
                emRegions.add(emRegion);
            }
        }
        if (!emRegions.isEmpty()) {
            TreeEM em = new TreeEM();
            emSheet.setWorkbook(em);
            emSheet.setRegions(emRegions);
            em.setSheets(Arrays.asList((ITreeEMSheet) emSheet));
            setWorkbookProperties(modelId, workbook, em);
            return em;
        }
        return null;
    }

    /**
     * 根据组件构建Excel模型
     * 
     * @param modelId
     * @param workbook
     * @param parser
     * @return
     */
    private static TreeEM buildTreeFormComponent(String modelId, Element workbook, ITreeEMComponentParser parser) {
        ITreeEMComponent component = parser.parse(modelId, workbook);
        if (null != component) {
            TreeEM em = new TreeEM();
            TreeEMSheet emSheet = new TreeEMSheet();
            TreeEMRegion emRegion = new TreeEMRegion();
            // 设置组件
            component.setRegion(emRegion);
            // 设置区域
            emRegion.setSheet(emSheet);
            emRegion.setComponent(component);
            // 设置表单
            emSheet.setRegions(Arrays.asList((ITreeEMRegion) emRegion));
            emSheet.setWorkbook(em);
            // 设置Excel模型
            em.setSheets(Arrays.asList((ITreeEMSheet) emSheet));
            setWorkbookProperties(modelId, workbook, em);
            return em;
        }
        return null;
    }

    /**
     * 设置workbook元素属性
     * 
     * @param modelId
     * @param workbook
     * @param em
     */
    private static void setWorkbookProperties(String modelId, Element workbook, TreeEM em) {
        if (!CoreUtils.isBlank(modelId)) {
            em.setId(modelId.trim());
        }

        String name = workbook.getAttribute(WORKBOOK_NAME);
        if (!CoreUtils.isBlank(name)) {
            em.setName(name.trim());
        }

        String desc = workbook.getAttribute(WORKBOOK_DESC);
        if (!CoreUtils.isBlank(desc)) {
            em.setDesc(desc.trim());
        }

        String prior = workbook.getAttribute(WORKBOOK_PRIOR);
        if (!CoreUtils.isBlank(prior)) {
            em.setPrior(Integer.parseInt(prior));
        }

        String textWorkbookRef = workbook.getAttribute(TEXT_WORKBOOK_REF);
        if (!CoreUtils.isBlank(textWorkbookRef)) {
            em.setTextWorkbookRef(textWorkbookRef.trim());
        }
    }

    /**
     * 解析一个sheet元素
     * 
     * @param modelId
     * @param sheet
     * @return
     */
    private static TreeEMSheet parseSheetElement(String modelId, Element sheet) {
        List<Element> regions = XmlHelper.getChildElementsByTagName(sheet, XmlEMLoaderConsts.REGION_ELEMENT_NAME);
        if (!regions.isEmpty()) {// 存在<region>元素
            return buildSheetFormRegions(modelId, sheet, regions);
        }

        // 不存在<region>，视作gird模型
        ITreeEMComponentParser parser = XmlEMLoaderConfig.getTreeEMComponentParser(XmlEMLoaderConsts.GRID_COMPONENT_TYPE);
        return null == parser ? null : buildSheetFormComponent(modelId, sheet, parser);
    }

    /**
     * 根据regions构建Sheet
     * 
     * @param modelId
     * @param sheet
     * @param regions
     * @return
     */
    private static TreeEMSheet buildSheetFormRegions(String modelId, Element sheet, List<Element> regions) {
        TreeEMSheet emSheet = new TreeEMSheet();
        List<ITreeEMRegion> emRegions = new ArrayList<ITreeEMRegion>();
        for (Element region : regions) {
            TreeEMRegion emRegion = parseRegionElement(modelId, region);
            if (null != emRegion) {
                emRegion.setSheet(emSheet);
                emRegions.add(emRegion);
            }
        }
        if (!emRegions.isEmpty()) {
            emSheet.setRegions(emRegions);
            setSheetProperties(sheet, emSheet);
            return emSheet;
        }
        return null;
    }

    /**
     * 根据组件构建Sheet
     * 
     * @param modelId
     * @param sheet
     * @param parser
     * @return
     */
    private static TreeEMSheet buildSheetFormComponent(String modelId, Element sheet, ITreeEMComponentParser parser) {
        ITreeEMComponent component = parser.parse(modelId, sheet);
        if (null != component) {
            TreeEMSheet emSheet = new TreeEMSheet();
            TreeEMRegion emRegion = new TreeEMRegion();
            // 设置组件
            component.setRegion(emRegion);
            // 设置区域
            emRegion.setSheet(emSheet);
            emRegion.setComponent(component);
            // 设置表单
            emSheet.setRegions(Arrays.asList((ITreeEMRegion) emRegion));
            setSheetProperties(sheet, emSheet);
            return emSheet;
        }
        return null;
    }

    /**
     * 设置sheet元素的属性
     * 
     * @param sheet
     * @param emSheet
     */
    private static void setSheetProperties(Element sheet, TreeEMSheet emSheet) {
        String name = sheet.getAttribute(SHEET_NAME);
        if (!CoreUtils.isBlank(name)) {
            emSheet.setSheetName(name.trim());
        }

        String condition = sheet.getAttribute(SHEET_CONDITION);
        if (!CoreUtils.isBlank(condition)) {
            emSheet.setCondition(condition.trim());
        }

        String expression = sheet.getAttribute(SHEET_EXPRESSION);
        if (!CoreUtils.isBlank(expression)) {
            emSheet.setExpression(expression.trim());
        }
    }

    /**
     * 设置区域元素的属性
     * 
     * @param region
     * @param emRegion
     */
    private static void setRegionProperties(Element region, TreeEMRegion emRegion) {
        String name = region.getAttribute(REGION_NAME);
        if (!CoreUtils.isBlank(name)) {
            emRegion.setName(name);
        }

        String title = region.getAttribute(REGION_TITLE);
        if (!CoreUtils.isBlank(title)) {
            emRegion.setTitle(title.trim());
        }

        String offsetName = region.getAttribute(REGION_OFFSET_NAME);
        if (!CoreUtils.isBlank(offsetName)) {
            emRegion.setOffsetName(offsetName);
        }

        String offsetPoint = region.getAttribute(REGION_OFFSET_POINT);
        if (!CoreUtils.isBlank(offsetPoint)) {
            emRegion.setOffsetPoint(Enum.valueOf(OffsetPoint.class, offsetPoint.toUpperCase()));
        } else {
            emRegion.setOffsetPoint(OffsetPoint.LEFT_BUTTOM);
        }

        String offsetX = region.getAttribute(REGION_OFFSET_X);
        if (!CoreUtils.isBlank(offsetX)) {
            emRegion.setOffsetX(Integer.parseInt(offsetX));
        } else {
            emRegion.setOffsetX(0);
        }

        String offsetY = region.getAttribute(REGION_OFFSET_Y);
        if (!CoreUtils.isBlank(offsetY)) {
            emRegion.setOffsetY(Integer.parseInt(offsetY));
        } else {
            emRegion.setOffsetY(0);
        }

        String condition = region.getAttribute(REGION_CONDITION);
        if (!CoreUtils.isBlank(condition)) {
            emRegion.setCondition(condition.trim());
        }

        String expression = region.getAttribute(REGION_EXPRESSION);
        if (!CoreUtils.isBlank(expression)) {
            emRegion.setExpression(expression.trim());
        }
    }

    /**
     * 获取解析器类型
     * <ul>
     * <li>根据region元素的type属性确定组件类型
     * <li>如果type属性为空，并且第一个子元素也是region元素，则做为嵌套组件类型
     * <li>如果type属性为空，并且第一个子元素不是region元素，则做为grid组件类型
     * </ul>
     * 
     * @param region
     * @return
     */
    private static String getParserType(Element region) {
        String type = region.getAttribute(REGION_TYPE);
        if (CoreUtils.isBlank(type)) {
            Element firstChild = XmlHelper.getChildElement(region);
            if (null != firstChild && XmlEMLoaderConsts.REGION_ELEMENT_NAME.equals(XmlHelper.getLocalName(firstChild))) {
                type = XmlEMLoaderConsts.NESTED_REGION_COMPONENT_TYPE;
            } else {
                type = XmlEMLoaderConsts.GRID_COMPONENT_TYPE;
            }
        }
        return type;
    }

    /**
     * 获取装饰器
     * 
     * @param ele
     * @return
     */
    private static ITreeEMDecorator getDecorator(Element ele) {
        String namespace = ele.getNamespaceURI();
        if (!XmlEMLoaderConfig.isDefaultNamespace(namespace)) {
            return XmlEMLoaderConfig.getTreeEMDecorator(namespace, ele.getLocalName());
        }
        return null;
    }
}
