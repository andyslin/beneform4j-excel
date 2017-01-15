package com.forms.beneform4j.excel.core.model.loader.xml.component.impl;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.xml.XmlHelper;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.core.model.em.tree.impl.TreeEMRegion;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.NestedRegionTreeEMComponent;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoader;
import com.forms.beneform4j.excel.core.model.loader.xml.component.ITreeEMComponentParser;
import com.forms.beneform4j.excel.core.model.loader.xml.workbook.impl.TreeWorkbookParserDelegate;

public class NestedRegionParser implements ITreeEMComponentParser {

    @Override
    public ITreeEMComponent parse(String modelId, Element container) {
        List<Element> elements = XmlHelper.getChildElementsByTagName(container, XmlEMLoader.REGION_ELEMENT_NAME);
        if (null != elements) {
            List<ITreeEMRegion> regions = new ArrayList<ITreeEMRegion>();
            for (Element ele : elements) {
                TreeEMRegion region = TreeWorkbookParserDelegate.parseRegionElement(modelId, ele);
                if (null != region) {
                    regions.add(region);
                }
            }
            if (!regions.isEmpty()) {
                NestedRegionTreeEMComponent component = new NestedRegionTreeEMComponent();
                component.setRegions(regions);
                return component;
            }
        }
        return null;
    }

}
