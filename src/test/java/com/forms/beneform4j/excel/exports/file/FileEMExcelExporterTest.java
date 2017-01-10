package com.forms.beneform4j.excel.exports.file;

import java.util.ArrayList;
<<<<<<< HEAD
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forms.beneform4j.excel.exports.IExcelExporter;
import com.forms.beneform4j.excel.exports.file.model.Department;
import com.forms.beneform4j.excel.exports.file.model.Employee;
import com.forms.beneform4j.excel.model.EMManager;
import com.forms.beneform4j.excel.model.base.IEM;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class FileEMExcelExporterTest {

    private IExcelExporter exporter = new FileEMExcelExporter();

    @Test
    public void testOneGrid1() throws Exception {
        test("two_inner_loops_demo");
    }

    private void test(String modelId) {
        try {
            String filename = "D:/excel-test/file/" + modelId + ".xls";
            IEM model = EMManager.load(modelId);
            List<Department> data = getTestData();
            exporter.export(model, data, filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Department> getTestData() {
        List<Department> departments = new ArrayList<Department>();
        Department department = new Department("IT");
        Employee chief = new Employee("Derek", 35, 3000, 0.30);
        department.setChief(chief);
        department.setLink("http://www.formssi.com");
        department.addEmployee(new Employee("Elsa", 28, 1500, 0.15));
        department.addEmployee(new Employee("Oleg", 32, 2300, 0.25));
        department.addEmployee(new Employee("Neil", 34, 2500, 0.00));
        department.addEmployee(new Employee("Maria", 34, 1700, 0.15));
        department.addEmployee(new Employee("John", 35, 2800, 0.20));
        departments.add(department);
        department = new Department("HR");
        chief = new Employee("Betsy", 37, 2200, 0.30);
        department.setChief(chief);
        department.setLink("http://www.formssi.com");
        department.addEmployee(new Employee("Olga", 26, 1400, 0.20));
        department.addEmployee(new Employee("Helen", 30, 2100, 0.10));
        department.addEmployee(new Employee("Keith", 24, 1800, 0.15));
        department.addEmployee(new Employee("Cat", 34, 1900, 0.15));
        departments.add(department);
        department = new Department("BA");
        chief = new Employee("Wendy", 35, 2900, 0.35);
        department.setChief(chief);
        department.setLink("http://www.formssi.com");
        department.addEmployee(new Employee("Denise", 30, 2400, 0.20));
        department.addEmployee(new Employee("LeAnn", 32, 2200, 0.15));
        department.addEmployee(new Employee("Natali", 28, 2600, 0.10));
        department.addEmployee(new Employee("Martha", 33, 2150, 0.25));
        departments.add(department);
        return departments;
=======
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forms.beneform4j.excel.exports.IExcelExporter;
import com.forms.beneform4j.excel.model.EMManager;
import com.forms.beneform4j.excel.model.base.IEM;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class FileEMExcelExporterTest {

    private IExcelExporter exporter = new FileEMExcelExporter();

    @Test
    public void testOneGrid1() throws Exception {
        test("oneGrid1");
    }

    private void test(String modelId) {
        try {
            String filename = "D:/excel-test/file/" + modelId + ".xlsx";
            IEM model = EMManager.load(modelId);
            List<Object> data = getTestData();
            exporter.export(model, data, filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Object> getTestData() {
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

        return list;
>>>>>>> branch 'master' of http://192.168.22.190:8090/beneform4j/beneform4j-excel.git
    }
}
