package com.forms.beneform4j.excel.model;

import java.util.List;

import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.model.base.IEM;
import com.forms.beneform4j.excel.model.base.IEMLoader;

public class EMManager {

	/**
	 * 加载Excel模型
	 * @param modelId
	 * @return
	 */
	public static IEM load(String modelId){
		List<IEMLoader> loaders = ExcelComponentConfig.getExcelModelLoaders();
		if(null != loaders){
			for(IEMLoader loader : loaders){
				IEM model = loader.load(modelId);
				if(null != model){
					return model;
				}
			}
		}
		return null;
	}
	
	/**
	 * 移除Excel模型
	 * @param modelId
	 */
	public static void remove(String modelId){
		List<IEMLoader> loaders = ExcelComponentConfig.getExcelModelLoaders();
		if(null != loaders){
			for(IEMLoader loader : loaders){
				loader.remove(modelId);
			}
		}
	}
	
	/**
	 * 从指定加载器中移除Excel模型
	 * @param loader
	 * @param modelId
	 */
	public static void remove(IEMLoader loader, String modelId){
		if(null != loader && null != modelId){
			loader.remove(modelId);
		}
	}
	
	/**
	 * 清除Excel模型
	 */
	public static void clear(){
		List<IEMLoader> loaders = ExcelComponentConfig.getExcelModelLoaders();
		if(null != loaders){
			for(IEMLoader loader : loaders){
				loader.clear();
			}
		}
	}
	
	/**
	 * 清除指定加载器中的Excel模型
	 * @param loader
	 */
	public static void clear(IEMLoader loader){
		if(null != loader){
			loader.clear();
		}
	}
}
