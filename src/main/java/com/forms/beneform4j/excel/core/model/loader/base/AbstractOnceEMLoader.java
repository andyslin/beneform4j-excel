package com.forms.beneform4j.excel.core.model.loader.base;

import java.util.concurrent.atomic.AtomicBoolean;

import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.loader.IEMLoadContext;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 一次性加载同类型所有Excel模型的抽象加载器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2016-12-22<br>
 */
public abstract class AbstractOnceEMLoader extends AbstractCacheableEMLoader {

    /**
     * 是否已经初始化加载的监控标志
     */
    private final AtomicBoolean loadMonitor = new AtomicBoolean(false);

    /**
     * 对于一次性加载所有模型的加载器来说，初始化之后已经存放到缓存中了，所以可以直接返回null，由父类从缓存中获取模型
     */
    @Override
    protected IEM doLoad(IEMLoadContext context, String modelId) {
        if (!loadMonitor.get()) {
            synchronized (loadMonitor) {
                if (!loadMonitor.get()) {
                    try {
                        this.init(context);
                    } finally {
                        loadMonitor.set(true);
                    }
                }
            }
        }
        return null;
    }

    /**
     * 初始化
     * 
     * @param context 加载上下文
     */
    protected abstract void init(IEMLoadContext context);
}
