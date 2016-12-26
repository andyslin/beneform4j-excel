package com.forms.beneform4j.excel.model.tree.loader;

import java.util.List;

import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.xml.XmlHelper;
import com.forms.beneform4j.core.util.xml.parser.XmlParserUtils;
import com.forms.beneform4j.excel.model.base.IEM;
import com.forms.beneform4j.excel.model.base.loader.AbstractResourceEMLoader;

public class XmlTreeEMLoader extends AbstractResourceEMLoader{

	@Override
	protected void registerExcelModel(Resource resource, boolean force) {
		Document document = XmlParserUtils.buildDocument(resource);
		List<Element> elements = XmlHelper.getChildElementsByTagName(document.getDocumentElement(), "workbook");
		if(null != elements){
			for(Element ele : elements){
				IEM em = XmlTreeEMParser.parse(ele);
				super.addEM(em, force);
			}
		}
	}
}
