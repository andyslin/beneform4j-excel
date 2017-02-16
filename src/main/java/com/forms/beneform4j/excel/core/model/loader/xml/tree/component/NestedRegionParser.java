package com.forms.beneform4j.excel.core.model.loader.xml.tree.component;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.xml.XmlHelper;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.core.model.em.tree.impl.TreeEMRegion;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.NestedRegionTreeEMComponent;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoaderConsts;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.ITreeEMComponentParser;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.TreeWorkbookParserDelegate;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 嵌套区域组件解析器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class NestedRegionParser implements ITreeEMComponentParser {

    @Override
    public ITreeEMComponent parse(String modelId, Element container) {
        List<Element> elements = XmlHelper.getChildElementsByTagName(container, XmlEMLoaderConsts.REGION_ELEMENT_NAME);
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
