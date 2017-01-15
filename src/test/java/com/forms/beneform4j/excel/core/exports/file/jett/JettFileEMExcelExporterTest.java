package com.forms.beneform4j.excel.core.exports.file.jett;

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

import com.forms.beneform4j.excel.core.exports.ExcelExporters;
import com.forms.beneform4j.excel.core.model.em.EMManager;
import com.forms.beneform4j.excel.core.model.em.IEM;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class JettFileEMExcelExporterTest {

    @Test
    public void testOneGrid1() throws Exception {
        test("jett-one-grid");

    }

    private void test(String modelId) {
        try {
            String filename = "D:/excel-test/jett/" + modelId + ".xls";
            IEM model = EMManager.load(modelId);
            Object data = getTestData();
            ExcelExporters.export(model, data, filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getTestData() {
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

        return list.iterator();
    }
}
