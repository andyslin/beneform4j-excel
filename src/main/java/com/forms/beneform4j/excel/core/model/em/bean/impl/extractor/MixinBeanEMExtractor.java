package com.forms.beneform4j.excel.core.model.em.bean.impl.extractor;

import org.apache.poi.ss.usermodel.Cell;

import com.forms.beneform4j.excel.core.model.em.bean.BeanEMExtractResult;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMExtractor;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMProperty;
import com.forms.beneform4j.excel.core.model.em.bean.impl.AbstractIdentifyMixin;
import com.forms.beneform4j.excel.core.model.em.bean.impl.BeanEMManager;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 混入其它实现的提取器实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class MixinBeanEMExtractor extends AbstractIdentifyMixin<IBeanEMExtractor> implements IBeanEMExtractor {

    /**
     * 
     */
    private static final long serialVersionUID = -6771732377745798833L;

    @Override
    public BeanEMExtractResult extract(IBeanEMProperty property, Cell cell, Class<?> type) {
        IBeanEMExtractor extractor = super.getInstance();
        if (null != extractor) {
            return extractor.extract(property, cell, type);
        }
        return null;
    }

    @Override
    protected IBeanEMExtractor getInstance(String id) {
        return BeanEMManager.getExtractor(id);
    }
}
