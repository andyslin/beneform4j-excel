package com.forms.beneform4j.excel.model.tree.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.core.util.xml.XmlHelper;
import com.forms.beneform4j.excel.model.tree.ITreeEMCell;
import com.forms.beneform4j.excel.model.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.model.tree.ITreeEMSheet;
import com.forms.beneform4j.excel.model.tree.em.TreeEM;
import com.forms.beneform4j.excel.model.tree.em.TreeEMCell;
import com.forms.beneform4j.excel.model.tree.em.TreeEMRegion;
import com.forms.beneform4j.excel.model.tree.em.TreeEMSheet;

public class XmlTreeEMParser {

    private static final String SHEET_NAME = "sheet";
    private static final String REGION_NAME = "region";
    private static final String CELL_NAME = "cell";

    public static TreeEM parse(Element element) {
        String name = XmlHelper.getLocalName(element);
        Map<String, List<Element>> map = XmlHelper.getChildElementsMapByTagName(element, SHEET_NAME, REGION_NAME, CELL_NAME);
        List<Element> sheets = map.get(SHEET_NAME);
        List<Element> regions = map.get(REGION_NAME);
        List<Element> cells = map.get(CELL_NAME);

        TreeEM em = null;
        if (!sheets.isEmpty()) {// 存在<sheet>元素
            if (!regions.isEmpty()) {
                Throw.throwRuntimeException("<" + name + ">元素不能同时具有<" + SHEET_NAME + ">和<" + REGION_NAME + ">两种直接子元素");
            } else if (!cells.isEmpty()) {
                Throw.throwRuntimeException("<" + name + ">元素不能同时具有<" + SHEET_NAME + ">和<" + CELL_NAME + ">两种直接子元素");
            }
            List<ITreeEMSheet> emSheets = new ArrayList<ITreeEMSheet>();
            for (Element sheet : sheets) {
                ITreeEMSheet emSheet = parseSheetElement(sheet);
                emSheets.add(emSheet);
            }
            em = TreeEMBuilder.buildTreeFormSheets(emSheets);
        } else if (!regions.isEmpty()) {// 不存在<sheet>，但存在<region>
            if (!cells.isEmpty()) {
                Throw.throwRuntimeException("<" + name + ">元素不能同时具有<" + REGION_NAME + ">和<" + CELL_NAME + ">两种直接子元素");
            }
            List<ITreeEMRegion> emRegions = parseRegionElements(regions);
            em = TreeEMBuilder.buildTreeFormRegions(emRegions);
        } else if (!cells.isEmpty()) {
            List<ITreeEMCell> emCells = parseCellElements(cells);
            em = TreeEMBuilder.buildTreeFormCells(emCells);
        } else {
            Throw.throwRuntimeException("<" + name + ">元素没有找到有效的<" + SHEET_NAME + ">或<" + REGION_NAME + ">或<" + CELL_NAME + ">直接子元素");
        }

        return em;
    }

    private static ITreeEMSheet parseSheetElement(Element sheet) {
        String name = XmlHelper.getLocalName(sheet);
        Map<String, List<Element>> map = XmlHelper.getChildElementsMapByTagName(sheet, REGION_NAME, CELL_NAME);
        List<Element> regions = map.get(REGION_NAME);
        List<Element> cells = map.get(CELL_NAME);

        TreeEMSheet emSheet = null;
        if (!regions.isEmpty()) {
            if (!cells.isEmpty()) {
                Throw.throwRuntimeException("<" + name + ">元素不能同时具有<" + REGION_NAME + ">和<" + CELL_NAME + ">两种直接子元素");
            }
            List<ITreeEMRegion> emRegions = parseRegionElements(regions);
            emSheet = TreeEMBuilder.buildSheetFormRegions(emRegions);
        } else if (!cells.isEmpty()) {
            List<ITreeEMCell> emCells = parseCellElements(cells);
            emSheet = TreeEMBuilder.buildSheetFormCells(emCells);
        } else {
            Throw.throwRuntimeException("<" + name + ">元素没有找到有效的<" + REGION_NAME + ">或<" + CELL_NAME + ">直接子元素");
        }

        return emSheet;
    }

    private static List<ITreeEMRegion> parseRegionElements(List<Element> regions) {
        List<ITreeEMRegion> emRegions = new ArrayList<ITreeEMRegion>();
        for (Element region : regions) {
            ITreeEMRegion emRegion = parseRegionElement(region);
            emRegions.add(emRegion);
        }
        return emRegions;
    }

    private static ITreeEMRegion parseRegionElement(Element region) {
        String name = XmlHelper.getLocalName(region);
        List<Element> cells = XmlHelper.getChildElementsByTagName(region, CELL_NAME);
        if (cells.isEmpty()) {
            Throw.throwRuntimeException("<" + name + ">元素没有找到有效的<" + CELL_NAME + ">直接子元素");
        }

        List<ITreeEMCell> emCells = parseCellElements(cells);
        TreeEMRegion emRegion = TreeEMBuilder.buildRegionFormCells(emCells);
        return emRegion;
    }

    private static List<ITreeEMCell> parseCellElements(List<Element> cells) {
        List<ITreeEMCell> emCells = new ArrayList<ITreeEMCell>();
        for (Element cell : cells) {
            ITreeEMCell emCell = parseCellElement(cell);
            emCells.add(emCell);
        }
        return emCells;
    }

    private static ITreeEMCell parseCellElement(Element cell) {
        TreeEMCell emCell = new TreeEMCell();

        return emCell;
    }
}
