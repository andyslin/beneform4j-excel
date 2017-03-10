package com.forms.beneform4j.excel.core.imports.bean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forms.beneform4j.excel.core.imports.ExcelImporters;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class ExcelImportsTest {

    @Test
    public void testImportBean() {
        String filename = "E:/workspace-git/beneform4j-excel/src/test/resources/excel/bean/Test.xls";
        ReqChangeFhBean bean = ExcelImporters.imports(filename, ReqChangeFhBean.class);
        System.out.println(bean);
    }

    @Test
    public void testImportBeanById() {
        String filename = "E:/workspace-git/beneform4j-excel/src/test/resources/excel/bean/Test.xls";
        ReqChangeFhBean bean = ExcelImporters.imports(filename, "ReqChangeFhBean", ReqChangeFhBean.class);
        System.out.println(bean);
    }
}
