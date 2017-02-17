package com.forms.beneform4j.excel.core.model.em.bean.impl;

import java.util.Map;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.model.em.EMManager;
import com.forms.beneform4j.excel.core.model.em.EMType;
import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEM;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMProperty;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Bean模型的代理实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
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
                    if (null == em) {
                        Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS07, proxyId);
                    } else if (em instanceof IBeanEM) {
                        proxy = (IBeanEM) em;
                    } else {
                        Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS08, em);
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
