package com.forms.beneform4j.excel.core.exports.dynamic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class DynamicEMExcelExporterTest {

    @Test
    public void testExport() throws Exception {
        test("dynamic-one-grid", 3);
        test("dynamic-one-grid", 5);
        test("dynamic-xml");
    }

    private void test(String modelId) {
        try {
            String filename = "D:/excel-test/dynamic/" + modelId + ".xlsx";
            IEM model = EMManager.load(modelId);
            Map<String, Object> param = getParam(0);
            List<Object> data = getDynamicData(100);
            ExcelExporters.exports(model, param, data, filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void test(String modelId, int count) {
        try {
            String filename = "D:/excel-test/dynamic/" + modelId + "(" + count + ").xlsx";
            IEM model = EMManager.load(modelId);
            Map<String, Object> param = getParam(count);
            List<Object> data = getTestData(100, count);
            ExcelExporters.exports(model, param, data, filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> getParam(int count) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("currentYear", 2015);
        List<Column> columns = new ArrayList<Column>();
        for (int i = 1; i <= count; i++) {
            Column c = new Column();
            columns.add(c);
            c.title = "嵌套列表标题" + i;
        }
        map.put("columns", columns);
        return map;
    }

    private List<Object> getTestData(int total, int count) {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < total; i++) {
            TestModel model = getTestModel(i, count);
            list.add(model);
        }
        return list;
    }

    private List<Object> getDynamicData(int total) {
        List<Object> list = new ArrayList<Object>();
        for (int i = 1; i <= total; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("cst_id", "1000" + i);
            map.put("cst_nm", "客户名称" + i);

            map.put("season", RandomUtils.nextInt(1, 4) + "");

            map.put("sr1", RandomUtils.nextDouble(0, 1) * 100);
            double sr2 = RandomUtils.nextDouble(0, 1) * 100;
            double sr3 = RandomUtils.nextDouble(0, 1) * 100;
            map.put("sr2", sr2);
            map.put("sr3", sr3);
            map.put("srTbbh", sr3 - sr2);

            map.put("lr1", RandomUtils.nextDouble(0, 1) * 100);
            double lr2 = RandomUtils.nextDouble(0, 1) * 100;
            double lr3 = RandomUtils.nextDouble(0, 1) * 100;
            map.put("lr2", lr2);
            map.put("lr3", lr3);
            map.put("lrTbbh", lr3 - lr2);

            map.put("zzc", RandomUtils.nextDouble(0, 1) * 100000);
            map.put("zfz", RandomUtils.nextDouble(0, 1) * 100000);

            list.add(map);
        }
        return list;
    }

    private static TestModel getTestModel(int index, int count) {
        TestModel model = new TestModel();
        model.simpleProperty = "简单属性值" + index;
        model.dynaProperty1 = "动态计算标题(1," + index + ")";
        model.dynaProperty2 = "动态计算标题(2," + index + ")";
        model.dynaProperty3 = "动态计算标题(3," + index + ")";
        model.nestProperty = new TestInnerModel();
        model.nestProperty.innerProperty = "嵌套属性值" + index;
        model.listProperty = new ArrayList<ListInnerModel>();
        for (int i = 1; i <= count; i++) {
            ListInnerModel lim = new ListInnerModel();
            lim.listInnerProperty = "嵌套列表属性值(" + index + "," + i + ")";
            model.listProperty.add(lim);
        }
        return model;
    }

    public static class TestModel {
        public String simpleProperty;
        public String dynaProperty1;
        public String dynaProperty2;
        public String dynaProperty3;
        public TestInnerModel nestProperty;
        public List<ListInnerModel> listProperty;
    }

    public static class TestInnerModel {
        public String innerProperty;
    }

    public static class ListInnerModel {
        public String listInnerProperty;
    }

    public static class Column {
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
