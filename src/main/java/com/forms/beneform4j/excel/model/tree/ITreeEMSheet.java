package com.forms.beneform4j.excel.model.tree;

import java.io.Serializable;
import java.util.List;

public interface ITreeEMSheet extends Serializable{

	/**
	 * 获取区域列表
	 * @return
	 */
	public List<ITreeEMRegion> getRegions();
	
	/**
	 * 获取表单名称
	 * @return
	 */
	public String getSheetName();
}
