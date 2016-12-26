package com.forms.beneform4j.excel.model.tree.em;

import java.util.List;

import com.forms.beneform4j.excel.model.tree.ITreeEMCell;
import com.forms.beneform4j.excel.model.tree.ITreeEMRow;

public class TreeEMRow implements ITreeEMRow{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8488195411154996295L;
	
	private List<ITreeEMCell> cells;
	
	@Override
	public List<ITreeEMCell> getCells() {
		return cells;
	}

	public void setCells(List<ITreeEMCell> cells) {
		this.cells = cells;
	}
}

