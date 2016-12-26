package com.forms.beneform4j.excel.export.grid.loader.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.cache.Cache;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.cache.Caches;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.core.util.xml.XmlHelper;
import com.forms.beneform4j.core.util.xml.context.IXmlParserContext;
import com.forms.beneform4j.core.util.xml.parser.INamespaceParser;
import com.forms.beneform4j.core.util.xml.parser.XmlParserUtils;
import com.forms.beneform4j.excel.export.grid.Grid;
import com.forms.beneform4j.excel.export.grid.Td;
import com.forms.beneform4j.excel.export.grid.loader.IGridModelLoader;

public class XmlGridModelLoader implements INamespaceParser<IXmlParserContext>, IGridModelLoader{

	public static Cache cache;
	private static final AtomicBoolean monitor = new AtomicBoolean(false);
	
	@Override
	public void parse(IXmlParserContext parserContext, Document document, Resource resource) {
		List<Element> nodes = XmlHelper.getChildElementsByTagName(document.getDocumentElement(), "grid");
		if(null == nodes){
			return ;
		}
		for(Element node : nodes){
			String modelId = node.getAttribute("id");
			if(CoreUtils.isBlank(modelId)){
				Throw.throwRuntimeException("Excel表格模型配置中缺少必须的id属性");
			}else{
				Grid grid = cache.get(modelId, Grid.class);
				if(null != grid){
					Throw.throwRuntimeException("Excel表格模型配置中id为"+modelId+"的配置重复");
				}else{
					grid = loadOneGridNode(node, modelId);
					cache.put(modelId, grid);
				}
			}
		}
	}

	@Override
	public Grid loadGridModel(String modelId) {
		if(!monitor.get()){
			synchronized(XmlGridModelLoader.class){
				if(!monitor.get()){
					try{
						cache = Caches.getCache(XmlGridModelLoader.class);
						XmlParserUtils.parseXml("classpath*:**/beneform4j-excel-export*.xml");
					}finally{
						monitor.set(true);
					}
				}
			}
		}
		
		Grid grid = null;
		if(null != cache){
			grid = cache.get(modelId, Grid.class);
		}
		if(null == grid){
			Throw.throwRuntimeException("未找到id为"+modelId+"的Excel表格模型配置");
		}
		return grid;
	}

	@Override
	public void init() {
		monitor.set(false);
	}
	
	private Grid loadOneGridNode(Element ele, String modelId) {
		List<Td> tds = new ArrayList<Td>();
		loadGridTd(-1, ele, tds);
		if(!tds.isEmpty()){
			Grid grid = new Grid();
		    grid.build(tds);
		    grid.setModelId(modelId);
		    grid.setModelName(ele.getAttribute("name"));
		    grid.setMemo(ele.getAttribute("text"));
		    return grid;
		}else{
			Throw.throwRuntimeException("配置文件中id为"+modelId+"的grid节点没有有效的td子节点（集）");
			return null;
		}
	}
	
	private void loadGridTd(int parentFieldSeqno, Element ele, List<Td> tds){
		int fieldSeqno = 0;
		if(parentFieldSeqno >= 0){
			Td td = new Td();
			tds.add(td);
			fieldSeqno = tds.size();
			td.setFieldSeqno(fieldSeqno);
			td.setParentFieldSeqno(parentFieldSeqno);
			setTdProperties(ele, td);
		}
		loadGridSubTds(ele, tds, fieldSeqno);
	}

	protected void setTdProperties(Element ele, Td td) {
		td.setDataType(ele.getAttribute("dataType"));
		td.setProperty(ele.getAttribute("property"));
		td.setFieldName(ele.getAttribute("text"));
		td.setDataFormat(ele.getAttribute("dataFormat"));
		td.setShowType(ele.getAttribute("showType"));
		td.setColumnWidth(ele.getAttribute("width"));
		td.setAlignType(ele.getAttribute("alignType"));
		td.setMemo(ele.getAttribute("memo"));
	}

	private void loadGridSubTds(Element ele, List<Td> tds, int fieldSeqno) {
		List<Element> subs = XmlHelper.getChildElementsByTagName(ele, "td");
		if(null != subs && !subs.isEmpty()){
			for(Element sub : subs){
				loadGridTd(fieldSeqno, sub, tds);
			}
		}
	}
}
