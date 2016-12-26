package com.forms.beneform4j.excel.model.base.loader;

import java.util.Set;

import org.springframework.core.io.Resource;

import com.forms.beneform4j.core.util.CoreUtils;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 从资源匹配模式表示的对象中加载Excel模型的抽象加载器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2016-12-22<br>
 */
public abstract class AbstractResourceEMLoader extends AbstractOnceEMLoader{

	/**
	 * 资源匹配模式
	 */
	private String[] locationPatterns;
	
	/**
	 * 注入资源匹配模式
	 * @param locationPatterns
	 */
	public void setLocationPatterns(String[] locationPatterns) {
		this.locationPatterns = locationPatterns;
	}

	/**
	 * 实现初始化方法
	 */
	@Override
	protected void init(boolean force) {
		if(null == locationPatterns || 0 == locationPatterns.length){
			return;
		}
		Set<Resource> resources = CoreUtils.getResources(locationPatterns);
		if(null != resources){
			for(Resource resource : resources){
				registerExcelModel(resource, force);
			}
		}
	}

	/**
	 * 解析一个资源，并注册Excel模型
	 * @param resource 资源对象
	 * @param force    是否强制注册
	 */
	protected abstract void registerExcelModel(Resource resource, boolean force);
}
