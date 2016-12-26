package com.forms.beneform4j.excel.model.tree.em;

import java.util.List;

import com.forms.beneform4j.excel.model.base.em.BaseEM;
import com.forms.beneform4j.excel.model.tree.ITreeEM;
import com.forms.beneform4j.excel.model.tree.ITreeEMSheet;

public class TreeEM extends BaseEM implements ITreeEM{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5158072882864528256L;
	
	private List<ITreeEMSheet> sheets;

	@Override
	public List<ITreeEMSheet> getSheets() {
		return sheets;
	}

	public void setSheets(List<ITreeEMSheet> sheets) {
		this.sheets = sheets;
	}
}
