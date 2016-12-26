package com.forms.beneform4j.excel.model.base.loader;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.cache.Cache;

import com.forms.beneform4j.core.util.cache.Caches;
import com.forms.beneform4j.core.util.logger.CommonLogger;
import com.forms.beneform4j.excel.model.base.IEM;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 可以缓存Excel模型的抽象加载器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2016-12-22<br>
 */
public abstract class AbstractCacheableEMLoader extends AbstractEMLoader{

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
	private boolean cacheable;
	
	/**
	 * 模型缓存容器
	 */
	private Cache cache;
	
	/**
	 * 是否已经初始化缓存的监控标志
	 */
	private final AtomicBoolean cacheMonitor = new AtomicBoolean(false);
	
	/**
	 * 注入是否强制注册的标志
	 * @param force
	 */
	public void setForce(boolean force) {
		this.force = force;
	}
	
	/**
	 * 是否强制注册
	 * @return
	 */
	public boolean isForce() {
		return force;
	}
	
	/**
	 * 设置加载器名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取加载器名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 是否启用缓存
	 * @return
	 */
	public boolean isCacheable() {
		return cacheable;
	}

	/**
	 * 设置是否启用缓存
	 * @param cacheable
	 */
	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

	/**
	 * 实现模型加载器接口：加载模型
	 */
	@Override
	final public IEM load(String modelId) {
		this.initCacheIfNeed();
		if(!this.isCacheable()){
			return this.doLoad(modelId, force);
		}else if(null == cache){
			return null;
		}else{
			IEM em = cache.get(modelId, IEM.class);
			if(null == em){
				synchronized(this){
					em = cache.get(modelId, IEM.class);
					if(null == em){
						em = this.doLoad(modelId, force);
						if(null != em){
							cache.put(modelId, em);
						}else{
							em = cache.get(modelId, IEM.class);
						}
						em = cache.get(modelId, IEM.class);
					}
				}
			}
			return em;
		}
	}
	
	/**
	 * 加载模型
	 * @param modelId 模型ID
	 * @param force   是否强制注册
	 * @return Excel模型
	 */
	protected abstract IEM doLoad(String modelId, boolean force);
	
	/**
	 * 初始化缓存容器
	 * @return
	 */
	protected Cache initCache(){
		return null;
	}

//	@Override
//	public Set<String> keys() {
//		if(null == cache){
//			return null;
//		}
//		return Caches.;
//	}

	/**
	 * 实现模型加载器接口：移除模型
	 */
	@Override
	public void remove(String modelId) {
		if(null != modelId && null != cache){
			cache.evict(modelId);
		}
	}

	/**
	 * 实现模型加载器接口：清除模型
	 */
	@Override
	public void clear() {
		if(null != cache){
			cache.clear();
		}
	}
	
	/**
	 * 添加Excel模型，如模型ID已经存在，剔除优先级低的
	 * @param em Excel模型
	 */
	protected void addEM(IEM em) {
		addEM(em, false);
	}
	
	/**
	 * 添加Excel模型，如模型ID已经存在，剔除优先级低的
	 * @param em     Excel模型
	 * @param force  是否强制添加，强制添加时不和已存在的模型比较优先级
	 */
	protected void addEM(IEM em, boolean force) {
		if(null != cache && isCacheable() && null != em){
			addEM(cache, em, force);
		}
	}

	/**
	 * 如果需要，初始化缓存容器
	 */
	private void initCacheIfNeed(){
		if(this.isCacheable() && !cacheMonitor.get()){
			synchronized(cacheMonitor){
				if(this.isCacheable() && !cacheMonitor.get()){
					try{
						Cache c = this.initCache();
						if(null == c){
							this.cache = Caches.getCache(this.getClass(), AbstractCacheableEMLoader.class.getName());
						}else{
							this.cache = c;
						}
					}finally{
						cacheMonitor.set(true);
					}
				}
			}
		}
	}
	
	/**
	 * 添加Excel模型
	 * @param cache 缓存容器
	 * @param em    Excel模型
	 * @param force 是否强制添加，强制添加时不比较优先级
	 */
	private void addEM(Cache cache, IEM em, boolean force){
		String modelId = em.getId();
		if(force){
			cache.put(modelId, em);
		}else{
			IEM old = cache.get(modelId, IEM.class);
			String loadername = null == name ? "annoy" : name; 
			if(null == old){
				cache.put(modelId, em);
				CommonLogger.info("[loader : "+loadername+"] register IEM with id is ["+ modelId+"], use {"+em+"}");
			}else if(old.getPrior() <= em.getPrior()){
				// 之前的缓存优先级高
				CommonLogger.warn("[loader : "+loadername+"] has more IEM with id is ["+modelId+"], according to the prior use {"+old+"}, ignore  {"+em+"]");
			}else{
				cache.put(modelId, em);
				CommonLogger.warn("[loader : "+loadername+"] has more IEM with id is ["+modelId+"], according to the prior use {"+em+"}, ignore {"+old+"}");
			}
		}
	}
}
