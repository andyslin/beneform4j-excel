package com.forms.beneform4j.excel.exports.file.jett;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forms.beneform4j.excel.exports.ExcelExporters;
import com.forms.beneform4j.excel.model.EMManager;
import com.forms.beneform4j.excel.model.base.IEM;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class JettFileEMExcelExporterTest {

    @Test
    public void testOneGrid1() throws Exception {
        test("NameTagTemplate");
    }

    private void test(String modelId) {
        try {
            String filename = "D:/excel-test/file/" + modelId + ".xls";
            IEM model = EMManager.load(modelId);
            Map<String, Object> data = getTestData();
            ExcelExporters.export(model, data, filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Map<String, Object> getTestData() {
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.putAll(TestUtility.getSpecificStateData(0, "state"));
        beans.putAll(TestUtility.getEmployeeData());
        return beans;
    }
}
