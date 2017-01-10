package com.forms.beneform4j.excel.exports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forms.beneform4j.excel.data.loader.impl.ImmutableDataDataLoader;
import com.forms.beneform4j.excel.model.EMManager;
import com.forms.beneform4j.excel.model.base.IEM;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class BaseExcelExporterTest {

    private IExcelExporter exporter = new BaseExcelExporter();

    @Test
    public void testExport() throws Exception {
        test("testDyanmicResource1");

        //        test("oneRegion1");
        //        test("oneRegion2");
        //        test("oneRegion3");
        //
        //        test("multiRegion1");
        //        test("multiRegion2");
        //        test("multiRegion3");
        //        test("multiRegion4");
        //        test("multiRegion5");
        //        test("multiRegion6");
    }

    private void test(String modelId) {
        try {
            String filename = "D:/excel-test/tree/" + modelId + ".xlsx";
            IEM model = EMManager.load(modelId);
            Object param = getParam();
            Object data = getTestData();
            ImmutableDataDataLoader dataLoader = new ImmutableDataDataLoader(data);
            exporter.export(model, dataLoader, param, filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getParam() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "可变的名称");
        return map;
    }

    private Object getTestData() {
        Map<String, Object> data = new HashMap<String, Object>();
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("charData", "固定长度" + RandomStringUtils.randomNumeric(8));// 定长文本
            map.put("varcharData", "可变长度" + RandomStringUtils.randomAlphanumeric(i % 2 * 4));
            map.put("hiddenData", "冻结区域隐藏数据");
            map.put("intData", i + 1);
            map.put("doubleData", RandomUtils.nextDouble(0, 1) * 100);
            map.put("percentagData", RandomUtils.nextDouble(0, 1));
            map.put("normalHiddenData", "普通区域隐藏数据");
            map.put("subData1", "跨行子数据1-" + (i + 1));
            map.put("subData2", "跨行子数据2-" + (i + 1));
            map.put("subData3", "跨行子数据3-" + (i + 1));
            list.add(map);
        }
        data.put("data", list);
        return data;
    }
}
