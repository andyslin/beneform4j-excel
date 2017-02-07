package com.forms.beneform4j.excel.core.imports.text;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forms.beneform4j.excel.core.imports.ExcelImporters;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class ExcelImportsTest {

    @Test
    public void testImportText() {
        String filename = "D:/excel-test/multi/paramDef(TREE-A-TEST).xlsx";
        Object value = ExcelImporters.imports(filename, "convert2txt1");
        System.out.println(value);
    }

    @Test
    public void testImportTree() {
        String modelId = "paramDef";
        String filename = "D:/excel-test/multi/paramDef(TREE-A-TEST).xlsx";
        Object value = ExcelImporters.imports(filename, modelId);
        System.out.println(value);
    }
}
