package com.forms.beneform4j.excel.db.imports;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class DbExcelImportersTest {

    @Test
    public void testImport() throws Exception {
        test("oneRegion1");
    }

    private void test(String modelId) {
        try {
            String location = "D:/excel-test/tree/oneGrid1.xlsx";
            DbExcelImporters.load(location, modelId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
