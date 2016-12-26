package com.forms.beneform4j.excel.export.render.impl;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.export.grid.Td;

public abstract class AbstractEnumParamRender extends BeanPropertyRender{

	private String paramCode;
	
	public AbstractEnumParamRender() {
	}
	
	public AbstractEnumParamRender(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	
	@Override
	public Object render(Object bean, Td td, int row, int column) {
		String dataCode = (String)super.render(bean, td, row, column);
		String paramCode = getParamCode();
		if(!CoreUtils.isBlank(dataCode) && !CoreUtils.isBlank(paramCode)){
			return getValue(paramCode, dataCode);
		}
		return dataCode;
	}
	
	abstract protected Object getValue(String paramCode, String dataCode);
}
