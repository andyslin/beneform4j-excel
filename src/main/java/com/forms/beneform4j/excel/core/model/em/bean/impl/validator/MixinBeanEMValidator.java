package com.forms.beneform4j.excel.core.model.em.bean.impl.validator;

import org.apache.poi.ss.usermodel.Cell;

import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMValidator;
import com.forms.beneform4j.excel.core.model.em.bean.impl.AbstractIdentifyMixin;
import com.forms.beneform4j.excel.core.model.em.bean.impl.BeanEMManager;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 混入其它实现的校验器实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class MixinBeanEMValidator extends AbstractIdentifyMixin<IBeanEMValidator> implements IBeanEMValidator {

    /**
     * 
     */
    private static final long serialVersionUID = 6244217949259032724L;

    @Override
    public void validate(Cell cell, Class<?> type) {
        IBeanEMValidator validator = getInstance();
        if (null != validator) {
            validator.validate(cell, type);
        }
    }

    @Override
    protected IBeanEMValidator getInstance(String id) {
        return BeanEMManager.getValidator(id);
    }
}
