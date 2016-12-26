package com.forms.beneform4j.excel.model.tree.loader;

import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Element;

import com.forms.beneform4j.excel.model.tree.ITreeEMCell;
import com.forms.beneform4j.excel.model.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.model.tree.ITreeEMSheet;
import com.forms.beneform4j.excel.model.tree.em.TreeEM;
import com.forms.beneform4j.excel.model.tree.em.TreeEMRegion;
import com.forms.beneform4j.excel.model.tree.em.TreeEMSheet;

public class TreeEMBuilder {
	
	public static TreeEMRegion buildRegionFormCells(List<ITreeEMCell> cells){
		if(null == cells || cells.isEmpty()){
			return null;
		}
		TreeEMRegion region = new TreeEMRegion();
		region.setCells(cells);
		return region;
	}
	
	public static TreeEMSheet buildSheetFormCells(List<ITreeEMCell> cells){
		if(null == cells || cells.isEmpty()){
			return null;
		}
		TreeEMRegion region = buildRegionFormCells(cells);
		return buildSheetFormRegion(region);
	}
	
	public static TreeEMSheet buildSheetFormRegion(ITreeEMRegion region){
		if(null == region){
			return null;
		}
		TreeEMSheet sheet = new TreeEMSheet();
		sheet.setRegions(Arrays.asList(region));
		return sheet;
	}
	
	public static TreeEMSheet buildSheetFormRegions(List<ITreeEMRegion> regions){
		if(null == regions || regions.isEmpty()){
			return null;
		}
		TreeEMSheet sheet = new TreeEMSheet();
		sheet.setRegions(regions);
		return sheet;
	}
	
	public static TreeEM buildTreeFormCells(List<ITreeEMCell> cells){
		if(null == cells || cells.isEmpty()){
			return null;
		}
		TreeEMSheet sheet = buildSheetFormCells(cells);
		return buildTreeFormSheet(sheet);
	}
	
	public static TreeEM buildTreeFormRegion(ITreeEMRegion region){
		if(null == region){
			return null;
		}
		TreeEMSheet sheet = buildSheetFormRegion(region);
		return buildTreeFormSheet(sheet);
	}
	
	public static TreeEM buildTreeFormRegions(List<ITreeEMRegion> regions){
		if(null == regions || regions.isEmpty()){
			return null;
		}
		TreeEMSheet sheet = buildSheetFormRegions(regions);
		return buildTreeFormSheet(sheet);
	}
	
	public static TreeEM buildTreeFormSheet(ITreeEMSheet sheet){
		if(null == sheet){
			return null;
		}
		TreeEM em = new TreeEM();
		em.setSheets(Arrays.asList(sheet));
		return em;
	}
	
	public static TreeEM buildTreeFormSheets(List<ITreeEMSheet> sheets){
		if(null == sheets || sheets.isEmpty()){
			return null;
		}
		TreeEM em = new TreeEM();
		em.setSheets(sheets);
		return em;
	}

	public static TreeEM buildTreeFormElement(Element element){
		return XmlTreeEMParser.parse(element);
	}
}
