package com.forms.beneform4j.excel.core.model.em.bean.impl.validator;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;

import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMValidator;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 复合校验器实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class CompositeBeanEMValidator implements IBeanEMValidator {

    /**
     * 
     */
    private static final long serialVersionUID = 4673204460405315595L;

    private List<IBeanEMValidator> validators;

    private boolean isOr;

    @Override
    public void validate(Cell cell, Class<?> fieldType) {
        List<IBeanEMValidator> validators = getValidators();
        if (null != validators && !validators.isEmpty()) {
            boolean isOr = isOr();
            for (IBeanEMValidator validator : validators) {
                try {
                    validator.validate(cell, fieldType);
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

    /**
     * 多个校验器是同时满足还是只需满足其中之一
     * 
     * @return
     */
    public boolean isOr() {
        return isOr;
    }

    public void setOr(boolean isOr) {
        this.isOr = isOr;
    }
}
