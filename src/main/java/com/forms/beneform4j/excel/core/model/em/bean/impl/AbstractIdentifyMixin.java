package com.forms.beneform4j.excel.core.model.em.bean.impl;

import java.util.List;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.reflect.object.impl.AbstractObjectMixin;

public abstract class AbstractIdentifyMixin<T> extends AbstractObjectMixin<T> {

    public String getMixinId() {
        return super.getClassName();
    }

    abstract protected T getInstance(String id);

    @Override
    public T getInstance(Object... constructorArgs) {
        T rs = getInstanceById(getMixinId());
        if (null != rs) {
            return rs;
        }
        return super.getInstance(constructorArgs);
    }

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
