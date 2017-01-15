package com.forms.beneform4j.excel.core.model.em.dynamic;

import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEM;

public interface IDynamicTreeEM extends IEM {

    public ITreeEM apply(Object param, Object data);
}
