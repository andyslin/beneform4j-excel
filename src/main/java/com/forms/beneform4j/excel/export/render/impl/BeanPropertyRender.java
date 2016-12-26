package com.forms.beneform4j.excel.export.render.impl;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.export.grid.Td;
import com.forms.beneform4j.excel.export.render.ITdRender;

public class BeanPropertyRender implements ITdRender{

	@Override
	public Object render(Object bean, Td td, int row, int column) {
		return CoreUtils.getProperty(bean, td.getProperty());
	}

}
