package com.forms.beneform4j.excel.model.base;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Excel模型加载器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2016-12-22<br>
 */
public interface IEMLoader {
	
	/**
	 * 加载Excel模型
	 * @param modelId
	 * @return
	 */
	public IEM load(String modelId);
	
//	/**
//	 * 获取所有的模型ID集合
//	 * @return
//	 */
//	public Set<String> keys();
	
	/**
	 * 移除Excel模型
	 * @param modelId
	 */
	public void remove(String modelId);
	
	/**
	 * 清空Excel模型
	 */
	public void clear();
}
