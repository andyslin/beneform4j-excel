package com.forms.beneform4j.excel.core.imports.bean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forms.beneform4j.excel.core.imports.IExcelImporter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class ExcelImportsTest {

    private IExcelImporter importer = new BeanEMExcelImporter();

    @Test
    public void testImportBean() {
        String filename = "E:/workspace-git/beneform4j-excel/src/test/resources/excel/imports/Test.xls";
        ReqChangeFhBean bean = importer.imports(filename, ReqChangeFhBean.class);
        System.out.println(bean);
    }

    @Test
    public void testImportBeanById() {
        String filename = "E:/workspace-git/beneform4j-excel/src/test/resources/excel/imports/Test.xls";
        ReqChangeFhBean bean = importer.imports(filename, "ReqChangeFhBean", ReqChangeFhBean.class);
        System.out.println(bean);
    }
}
