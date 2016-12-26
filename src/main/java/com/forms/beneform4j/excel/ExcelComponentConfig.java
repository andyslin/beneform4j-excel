package com.forms.beneform4j.excel;

import java.util.List;

import org.springframework.cache.Cache;

import com.forms.beneform4j.core.util.cache.Caches;
import com.forms.beneform4j.excel.model.base.IEM;
import com.forms.beneform4j.excel.model.base.IEMLoader;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Excel组件配置<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2016-12-22<br>
 */
public class ExcelComponentConfig {

	/**
	 * Excel模型的缓存容器
	 */
	private static Cache excelModelCache;
	
	/**
	 * Excel模型的加载器列表
	 */
	private static List<IEMLoader> eMLoaders;
	
	/**
	 * Excel模型的缓存容器
	 * @return
	 */
	public static Cache getExcelModelCache() {
		if(null == excelModelCache){
			excelModelCache = Caches.getCache(IEM.class);
		}
		return excelModelCache;
	}

	/**
	 * 注入Excel模型的缓存容器
	 * @param excelModelCache
	 */
	public void setExcelModelCache(Cache excelModelCache) {
		ExcelComponentConfig.excelModelCache = excelModelCache;
	}

	/**
	 * 获取Excel模型加载器列表
	 * @return
	 */
	public static List<IEMLoader> getExcelModelLoaders() {
		return eMLoaders;
	}

	/**
	 * 注入Excel模型加载器列表
	 * @param eMLoaders
	 */
	public void setExcelModelLoaders(List<IEMLoader> eMLoaders) {
		ExcelComponentConfig.eMLoaders = eMLoaders;
	}
}
