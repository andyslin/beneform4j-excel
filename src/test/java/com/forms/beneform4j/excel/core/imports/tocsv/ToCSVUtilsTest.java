package com.forms.beneform4j.excel.core.imports.tocsv;

import org.junit.Test;

public class ToCSVUtilsTest {

    @Test
    public void tocsvN() {
        String excel = "D:/excel-test/multi/paramDef(TREE-N-TEST).xlsx";
        String csv = "D:/excel-test/multi/paramDef(TREE-N-TEST).csv";
        ToCSVUtils.xls2csv(excel, csv);
    }

    @Test
    public void tocsvA() {
        String excel = "D:/excel-test/multi/paramDef(TREE-A-TEST).xlsx";
        String csv = "D:/excel-test/multi/paramDef(TREE-A-TEST).csv";
        ToCSVUtils.xls2csv(excel, csv);
    }
}
