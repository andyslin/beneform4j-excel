package com.forms.beneform4j.excel.core.model.em.bean.impl.matcher;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMMatcher;
import com.forms.beneform4j.excel.core.model.em.bean.impl.AbstractIdentifyMixin;
import com.forms.beneform4j.excel.core.model.em.bean.impl.BeanEMManager;

public class MixinBeanEMMatcher extends AbstractIdentifyMixin<IBeanEMMatcher> implements IBeanEMMatcher {

    @Override
    public boolean isMatch(Workbook workbook, Sheet sheet, Row row, Cell cell) {
        IBeanEMMatcher matcher = getInstance();
        if (null != matcher) {
            return matcher.isMatch(workbook, sheet, row, cell);
        }
        return false;
    }

    @Override
    protected IBeanEMMatcher getInstance(String id) {
        return BeanEMManager.getMatcher(id);
    }
}
