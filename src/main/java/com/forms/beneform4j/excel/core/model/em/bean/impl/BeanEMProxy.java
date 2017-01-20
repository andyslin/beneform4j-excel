package com.forms.beneform4j.excel.core.model.em.bean.impl;

import java.util.Map;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.model.em.EMManager;
import com.forms.beneform4j.excel.core.model.em.EMType;
import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEM;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMProperty;

public class BeanEMProxy implements IBeanEM {

    /**
     * 
     */
    private static final long serialVersionUID = 7412237241134573567L;

    private final String proxyId;

    private IBeanEM proxy;

    public BeanEMProxy(String proxyId) {
        super();
        this.proxyId = proxyId;
    }

    private void sureProxy() {
        if (null == proxy) {
            synchronized (this) {
                if (null == proxy) {
                    IEM em = EMManager.load(proxyId);
                    if (em instanceof IBeanEM) {
                        proxy = (IBeanEM) em;
                    } else {
                        Throw.throwRuntimeException("未找到id为" + proxyId + "的模型");
                    }
                }
            }
        }
    }

    @Override
    public String getId() {
        sureProxy();
        return proxy.getId();
    }

    @Override
    public String getName() {
        sureProxy();
        return proxy.getName();
    }

    @Override
    public EMType getType() {
        sureProxy();
        return proxy.getType();
    }

    @Override
    public void setType(EMType type) {
        sureProxy();
        proxy.setType(type);
    }

    @Override
    public String getDesc() {
        sureProxy();
        return proxy.getDesc();
    }

    @Override
    public int getPrior() {
        sureProxy();
        return proxy.getPrior();
    }

    @Override
    public Map<String, IBeanEMProperty> getProperties() {
        sureProxy();
        return proxy.getProperties();
    }

    @Override
    public Class<?> getBeanType() {
        sureProxy();
        return proxy.getBeanType();
    }
}