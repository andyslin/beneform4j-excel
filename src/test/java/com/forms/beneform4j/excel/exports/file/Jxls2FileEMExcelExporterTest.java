package com.forms.beneform4j.excel.exports.file;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forms.beneform4j.excel.exports.IExcelExporter;
import com.forms.beneform4j.excel.exports.file.impl.Jxls2FileEMExcelExporterDelegate;
import com.forms.beneform4j.excel.exports.file.model.Department;
import com.forms.beneform4j.excel.exports.file.model.Employee;
import com.forms.beneform4j.excel.model.EMManager;
import com.forms.beneform4j.excel.model.base.IEM;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class Jxls2FileEMExcelExporterTest {

    private IExcelExporter exporter = new Jxls2FileEMExcelExporterDelegate();

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
    }
}
