package com.forms.beneform4j.excel.core.model.em.bean.impl.validator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMValidator;
import com.forms.beneform4j.excel.core.model.em.bean.impl.AbstractIdentifyMixin;
import com.forms.beneform4j.excel.core.model.em.bean.impl.BeanEMManager;

public class MixinBeanEMValidator extends AbstractIdentifyMixin<IBeanEMValidator> implements IBeanEMValidator {

    /**
     * 
     */
    private static final long serialVersionUID = 6244217949259032724L;

    @Override
    public void validate(Workbook workbook, Sheet sheet, Row row, Cell cell, Class<?> type) {
        IBeanEMValidator validator = getInstance();
        if (null != validator) {
            validator.validate(workbook, sheet, row, cell, type);
        }
    }

    @Override
    protected IBeanEMValidator getInstance(String id) {
        return BeanEMManager.getValidator(id);
    }
}
