package com.forms.beneform4j.excel.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import com.forms.beneform4j.excel.export.builder.ModelExcelBuilderParam;
import com.forms.beneform4j.excel.export.datastream.wrap.impl.IteratorDataStreamHandlerWrap;
import com.forms.beneform4j.excel.export.grid.Grid;
import com.forms.beneform4j.excel.export.grid.GridFactory;

public class ExcelExportTest {

	@Test
	public void testName() throws Exception {
		
		Grid grid = GridFactory.getGrid("example");
		
		ModelExcelBuilderParam param = new ModelExcelBuilderParam();
		param.setGrid(grid);//excel模型
		param.setWrap(new IteratorDataStreamHandlerWrap(getTestData()));//大数据处理器
		param.setAutoFilter(true);//添加数据过滤
		Workbook wb = ExcelExport.build(param);
		ExcelUtils.write(wb, "D:/test.xlsx");
	}
	
	private List<Object> getTestData(){
		List<Object> list = new ArrayList<Object>();
		for(int i = 0; i<10; i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("charData", "固定长度"+RandomStringUtils.randomNumeric(8));//定长文本
			map.put("varcharData", "可变长度"+ RandomStringUtils.randomAlphanumeric(i%2*4));
			map.put("hiddenData", "冻结区域隐藏数据");
			map.put("intData", i+1);
			map.put("doubleData", RandomUtils.nextDouble(0, 1)*100);
			map.put("percentagData", RandomUtils.nextDouble(0, 1));
			map.put("normalHiddenData", "普通区域隐藏数据");
			map.put("subData1", "跨行子数据1-"+(i+1));
			map.put("subData2", "跨行子数据2-"+(i+1));
			map.put("subData3", "跨行子数据3-"+(i+1));
			list.add(map);
		}
		
		return list;
	}
}
