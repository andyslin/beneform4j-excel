package com.forms.beneform4j.excel.core.model.loader.base;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.cache.Cache;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.cache.Caches;
import com.forms.beneform4j.core.util.logger.CommonLogger;
import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.loader.IEMLoadContext;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 可以缓存Excel模型的抽象加载器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2016-12-22<br>
 */
public abstract class AbstractCacheableEMLoader extends AbstractEMLoader {

    /**
     * 加载器名称
     */
    private String name;

    /**
     * 是否强制注册（为true时注册时不比较相同ID其它模型的优先级）
     */
    private boolean force;

    /**
     * 是否启用缓存
     */
    private boolean cacheable = true;

    /**
     * 模型缓存容器
     */
    private Cache cache;

    /**
     * 缓存名称
     */
    private String cacheName;

    /**
     * 是否已经初始化缓存的监控标志
     */
    private final AtomicBoolean cacheMonitor = new AtomicBoolean(false);

    /**
     * 加载上下文
     */
    private final IEMLoadContext context = new IEMLoadContext() {
        @Override
        public void register(IEM model) {
            if (null != cache && isCacheable() && null != model) {
                addEM(cache, model, force);
            }
        }
    };

    /**
     * 注入是否强制注册的标志
     * 
     * @param force
     */
    public void setForce(boolean force) {
        this.force = force;
    }

    /**
     * 是否强制注册
     * 
     * @return
     */
    public boolean isForce() {
        return force;
    }

    /**
     * 设置加载器名称
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取加载器名称
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 是否启用缓存
     * 
     * @return
     */
    public boolean isCacheable() {
        return cacheable;
    }

    /**
     * 设置是否启用缓存
     * 
     * @param cacheable
     */
    public void setCacheable(boolean cacheable) {
        this.cacheable = cacheable;
    }

    /**
     * 缓存容器名称
     * 
     * @return 缓存容器名称
     */
    public String getCacheName() {
        return cacheName;
    }

    /**
     * 设置缓存容器名称
     * 
     * @param cacheName 缓存容器名称
     */
    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    /**
     * 实现模型加载器接口：加载模型
     */
    @Override
    final public IEM load(String modelId) {
        this.initCacheIfNeed();
        if (!this.isCacheable() || null == cache) {
            return this.doLoad(context, modelId);
        } else {
            IEM em = cache.get(modelId, IEM.class);
            if (null == em) {
                synchronized (this) {
                    em = cache.get(modelId, IEM.class);
                    if (null == em) {
                        em = this.doLoad(context, modelId);
                        if (null != em) {
                            this.context.register(em);
                        } else {
                            em = cache.get(modelId, IEM.class);
                        }
                    }
                }
            }
            return em;
        }
    }

    /**
     * 加载模型
     * 
     * @param context 加载上下文
     * @param modelId 模型ID
     * @return Excel模型
     */
    protected abstract IEM doLoad(IEMLoadContext context, String modelId);

    /**
     * 初始化缓存容器
     * 
     * @return
     */
    protected Cache initCache() {
        return null;
    }

    /**
     * 实现模型加载器接口：移除模型
     */
    @Override
    public void remove(String modelId) {
        if (null != modelId && null != cache) {
            cache.evict(modelId);
        }
    }

    /**
     * 实现模型加载器接口：清除模型
     */
    @Override
    public void clear() {
        if (null != cache) {
            cache.clear();
        }
    }

    /**
     * 如果需要，初始化缓存容器
     */
    private void initCacheIfNeed() {
        if (this.isCacheable() && !cacheMonitor.get()) {
            synchronized (cacheMonitor) {
                if (this.isCacheable() && !cacheMonitor.get()) {
                    try {
                        Cache c = this.initCache();
                        if (null == c) {
                            String cacheName = getCacheName();
                            if (CoreUtils.isBlank(cacheName)) {
                                cacheName = this.getClass() + "###" + AbstractCacheableEMLoader.class.getName();
                            }
                            this.cache = Caches.getCache(cacheName);
                        } else {
                            this.cache = c;
                        }
                    } finally {
                        cacheMonitor.set(true);
                    }
                }
            }
        }
    }

    /**
     * 添加Excel模型
     * 
     * @param cache 缓存容器
     * @param em Excel模型
     * @param force 是否强制添加，强制添加时不比较优先级
     */
    private void addEM(Cache cache, IEM em, boolean force) {
        String modelId = em.getId();
        if (null == modelId) {
            return;
        } else if (force) {
            cache.put(modelId, em);
        } else {
            IEM old = cache.get(modelId, IEM.class);
            String loadername = null == name ? "annoy" : name;
            if (null == old) {
                cache.put(modelId, em);
                CommonLogger.info("[loader : " + loadername + "] register IEM  [" + em + "]");
            } else if (old.getPrior() <= em.getPrior()) {
                // 之前的缓存优先级高
                CommonLogger.warn("[loader : " + loadername + "] has more IEM with id is [" + modelId + "], according to the prior use [" + old + "], ignore [" + em + "]");
            } else {
                cache.put(modelId, em);
                CommonLogger.warn("[loader : " + loadername + "] has more IEM with id is [" + modelId + "], according to the prior use [" + em + "], ignore [" + old + "]");
            }
        }
    }
}
