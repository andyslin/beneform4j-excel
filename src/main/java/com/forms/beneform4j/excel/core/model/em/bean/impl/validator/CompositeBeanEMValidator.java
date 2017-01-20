package com.forms.beneform4j.excel.core.model.em.bean.impl.validator;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMValidator;

public class CompositeBeanEMValidator implements IBeanEMValidator {

    /**
     * 
     */
    private static final long serialVersionUID = 4673204460405315595L;

    private List<IBeanEMValidator> validators;

    private boolean isOr;

    @Override
    public void validate(Workbook workbook, Sheet sheet, Row row, Cell cell, Class<?> fieldType) {
        List<IBeanEMValidator> validators = getValidators();
        if (null != validators && !validators.isEmpty()) {
            boolean isOr = isOr();
            for (IBeanEMValidator validator : validators) {
                try {
                    validator.validate(workbook, sheet, row, cell, fieldType);
                    if (isOr) {
                        return;
                    }
                } catch (Exception e) {
                    if (!isOr) {
                        throw e;
                    }
                }
            }
        }
    }

    public List<IBeanEMValidator> getValidators() {
        return validators;
    }

    public void setValidators(List<IBeanEMValidator> validators) {
        this.validators = validators;
    }

    public boolean isOr() {
        return isOr;
    }

    public void setOr(boolean isOr) {
        this.isOr = isOr;
    }
}
