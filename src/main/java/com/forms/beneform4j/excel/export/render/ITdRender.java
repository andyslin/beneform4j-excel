package com.forms.beneform4j.excel.export.render;

import com.forms.beneform4j.excel.export.grid.Td;

public interface ITdRender {

	public Object render(Object bean, Td td, int row, int column);
}
