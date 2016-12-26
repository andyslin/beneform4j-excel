package com.forms.beneform4j.excel.model.dynamic;

import com.forms.beneform4j.excel.model.base.IEM;
import com.forms.beneform4j.excel.model.tree.ITreeEM;

public interface IDynamicTreeEM extends IEM{

	public ITreeEM apply(Object param);
}
