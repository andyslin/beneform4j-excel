package com.forms.beneform4j.excel.model.tree.loader.xml;

import org.w3c.dom.Element;

import com.forms.beneform4j.excel.model.tree.ITreeEMComponent;

public interface ITreeEMComponentParser {

    public ITreeEMComponent parse(String modelId, Element container);
}
