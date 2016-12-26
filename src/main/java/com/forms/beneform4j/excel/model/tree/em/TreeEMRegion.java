package com.forms.beneform4j.excel.model.tree.em;

import java.util.List;

import com.forms.beneform4j.excel.model.tree.ITreeEMCell;
import com.forms.beneform4j.excel.model.tree.ITreeEMRegion;

public class TreeEMRegion implements ITreeEMRegion{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8807943325369934948L;
	
	private List<ITreeEMCell> cells;
	
	@Override
	public List<ITreeEMCell> getCells() {
		return cells;
	}

	public void setCells(List<ITreeEMCell> cells) {
		this.cells = cells;
	}
}
