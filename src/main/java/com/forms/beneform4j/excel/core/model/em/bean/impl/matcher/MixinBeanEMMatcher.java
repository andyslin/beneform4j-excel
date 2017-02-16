package com.forms.beneform4j.excel.core.model.em.bean.impl.matcher;

import org.apache.poi.ss.usermodel.Cell;

import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMMatcher;
import com.forms.beneform4j.excel.core.model.em.bean.impl.AbstractIdentifyMixin;
import com.forms.beneform4j.excel.core.model.em.bean.impl.BeanEMManager;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 混入其它实现的匹配器实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class MixinBeanEMMatcher extends AbstractIdentifyMixin<IBeanEMMatcher> implements IBeanEMMatcher {

    @Override
    public boolean isMatch(Cell cell) {
        IBeanEMMatcher matcher = getInstance();
        if (null != matcher) {
            return matcher.isMatch(cell);
        }
        return false;
    }

    @Override
    protected IBeanEMMatcher getInstance(String id) {
        return BeanEMManager.getMatcher(id);
    }
}
