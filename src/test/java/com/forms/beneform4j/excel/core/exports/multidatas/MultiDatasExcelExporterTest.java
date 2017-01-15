package com.forms.beneform4j.excel.core.exports.multidatas;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forms.beneform4j.core.dao.stream.IListStreamReader;
import com.forms.beneform4j.excel.core.data.dao.DbDataService;
import com.forms.beneform4j.excel.core.data.dao.ParamEnumDef;
import com.forms.beneform4j.excel.core.exports.ExcelExporters;
import com.forms.beneform4j.excel.core.model.em.EMManager;
import com.forms.beneform4j.excel.core.model.em.IEM;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class MultiDatasExcelExporterTest {

    @Test
    public void testExport() throws Exception {

        testTree("A-TEST"); // 数据量100（一百级）
        //        testTree("D-TEST"); // 数据量1000（一千级）
        //        testTree("G-TEST"); // 数据量10000(一万级）
        //        testTree("K-TEST"); // 数据量100000(十万级）
        //        testTree("L-TEST"); // 数据量200000(二十万级）
        //        testTree("N-TEST"); // 数据量1000000(百万级）

        testJett("A-TEST"); // 数据量100（一百级）
        testJett("D-TEST"); // 数据量1000（一千级）
        testJett("G-TEST"); // 数据量10000(一万级）
        testJett("K-TEST"); // 数据量100000(十万级）
        //        testJett("L-TEST"); // 数据量200000(二十万级）
        //        testJett("N-TEST"); // 数据量1000000(百万级）
    }

    private void testTree(String paramCode) {
        try {
            long s = System.currentTimeMillis();
            String modelId = "paramDef";
            String filename = "D:/excel-test/multi/" + modelId + "(TREE-" + paramCode + ")" + ".xls";
            IEM model = EMManager.load(modelId);
            IListStreamReader<ParamEnumDef> data = DbDataService.getData(paramCode);
            ExcelExporters.export(model, data.iterator(), filename);
            System.out.println((System.currentTimeMillis() - s) / 1000.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testJett(String paramCode) {
        try {
            long s = System.currentTimeMillis();
            String modelId = "jett-one-grid-iterator";
            String filename = "D:/excel-test/multi/" + modelId + "(JETT-" + paramCode + ")" + ".xls";
            IEM model = EMManager.load(modelId);
            Object data = DbDataService.getData(paramCode);
            ExcelExporters.export(model, data, filename);
            System.out.println((System.currentTimeMillis() - s) / 1000.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
