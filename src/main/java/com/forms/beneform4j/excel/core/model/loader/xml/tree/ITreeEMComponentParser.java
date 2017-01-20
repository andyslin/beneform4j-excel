package com.forms.beneform4j.excel.core.model.loader.xml.tree;

import org.w3c.dom.Element;

import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;

public interface ITreeEMComponentParser {

    public ITreeEMComponent parse(String modelId, Element container);
}
