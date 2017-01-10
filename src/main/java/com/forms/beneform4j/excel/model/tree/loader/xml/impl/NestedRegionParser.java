package com.forms.beneform4j.excel.model.tree.loader.xml.impl;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.xml.XmlHelper;
import com.forms.beneform4j.excel.model.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.model.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.model.tree.component.NestedRegionTreeEMComponent;
import com.forms.beneform4j.excel.model.tree.em.TreeEMRegion;
import com.forms.beneform4j.excel.model.tree.loader.xml.ITreeEMComponentParser;
import com.forms.beneform4j.excel.model.tree.loader.xml.XmlTreeEMConsts;
import com.forms.beneform4j.excel.model.tree.loader.xml.XmlTreeEMParserDelegate;

public class NestedRegionParser implements ITreeEMComponentParser {

    @Override
    public ITreeEMComponent parse(String modelId, Element container) {
        List<Element> elements = XmlHelper.getChildElementsByTagName(container, XmlTreeEMConsts.REGION);
        if (null != elements) {
            List<ITreeEMRegion> regions = new ArrayList<ITreeEMRegion>();
            for (Element ele : elements) {
                TreeEMRegion region = XmlTreeEMParserDelegate.parseRegionElement(modelId, ele);
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
