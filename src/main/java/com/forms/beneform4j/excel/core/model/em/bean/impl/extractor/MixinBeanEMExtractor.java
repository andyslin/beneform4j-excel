package com.forms.beneform4j.excel.core.model.em.bean.impl.extractor;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.forms.beneform4j.excel.core.model.em.bean.BeanEMExtractResult;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMExtractor;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMProperty;
import com.forms.beneform4j.excel.core.model.em.bean.impl.AbstractIdentifyMixin;
import com.forms.beneform4j.excel.core.model.em.bean.impl.BeanEMManager;

public class MixinBeanEMExtractor extends AbstractIdentifyMixin<IBeanEMExtractor> implements IBeanEMExtractor {

    /**
     * 
     */
    private static final long serialVersionUID = -6771732377745798833L;

    @Override
    public BeanEMExtractResult extract(IBeanEMProperty property, Workbook workbook, Sheet sheet, Row row, Cell cell, Class<?> type) {
        IBeanEMExtractor extractor = super.getInstance();
        if (null != extractor) {
            return extractor.extract(property, workbook, sheet, row, cell, type);
        }
        return null;
    }

    @Override
    protected IBeanEMExtractor getInstance(String id) {
        return BeanEMManager.getExtractor(id);
    }
}
