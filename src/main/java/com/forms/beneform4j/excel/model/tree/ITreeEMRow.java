package com.forms.beneform4j.excel.model.tree;

import java.io.Serializable;
import java.util.List;

public interface ITreeEMRow extends Serializable{

	public List<ITreeEMCell> getCells();
}
