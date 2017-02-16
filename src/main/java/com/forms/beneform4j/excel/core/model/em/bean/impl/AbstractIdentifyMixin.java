package com.forms.beneform4j.excel.core.model.em.bean.impl;

import java.util.List;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.reflect.object.impl.AbstractObjectMixin;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 抽象的可标识的混入对象<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public abstract class AbstractIdentifyMixin<T> extends AbstractObjectMixin<T> {

    /**
     * 获取混入ID
     * 
     * @return
     */
    public String getMixinId() {
        return super.getClassName();
    }

    /**
     * 根据混入ID获取实例，由子类实现
     * 
     * @param id
     * @return
     */
    abstract protected T getInstance(String id);

    /**
     * {@inheritDoc}
     */
    @Override
    public T getInstance(Object... constructorArgs) {
        T rs = getInstanceById(getMixinId());
        if (null != rs) {
            return rs;
        }
        return super.getInstance(constructorArgs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getInstance(List<Class<?>> constructorArgTypes, Object... constructorArgs) {
        T rs = getInstanceById(getMixinId());
        if (null != rs) {
            return rs;
        }
        return super.getInstance(constructorArgTypes, constructorArgs);
    }

    private T getInstanceById(String id) {
        try {
            if (!CoreUtils.isBlank(id)) {
                T rs = getInstance(id);
                if (null != rs) {
                    return rs;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
}
