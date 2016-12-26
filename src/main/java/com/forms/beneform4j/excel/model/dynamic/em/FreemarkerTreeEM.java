package com.forms.beneform4j.excel.model.dynamic.em;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.core.util.xml.parser.XmlParserUtils;
import com.forms.beneform4j.excel.model.base.em.BaseEM;
import com.forms.beneform4j.excel.model.dynamic.IDynamicTreeEM;
import com.forms.beneform4j.excel.model.tree.ITreeEM;
import com.forms.beneform4j.excel.model.tree.loader.XmlTreeEMParser;
import com.forms.beneform4j.util.Tool;

public class FreemarkerTreeEM extends BaseEM implements IDynamicTreeEM{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1655847880683121149L;
	
	private final String ftl;
	
	private final String type;
	
	public FreemarkerTreeEM(String ftl) {
		this(ftl, "content");
	}

	public FreemarkerTreeEM(String ftl, String type) {
		this.ftl = ftl;
		this.type = type;
	}

	@Override
	public ITreeEM apply(Object param) {
		Document document = null;
		if("content".equalsIgnoreCase(type)){
			String xmlString = Tool.TEMPLATE.fillStringFtl2String(ftl, param);
			Resource resource = new ByteArrayResource(xmlString.getBytes());
			document = XmlParserUtils.buildDocument(resource);
		}else if("location".equalsIgnoreCase(type)){
			
		}else if("classpath".equalsIgnoreCase(type)){
			
		}else{
			Throw.throwRuntimeException("UnSupported Freemarker Type.");
		}
		
		Element element = document.getDocumentElement();
		return XmlTreeEMParser.parse(element);
	}
	
	
}
