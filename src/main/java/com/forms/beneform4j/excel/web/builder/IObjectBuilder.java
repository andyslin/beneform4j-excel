package com.forms.beneform4j.excel.web.builder;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 对象生成器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2013-10-6<br>
 */
public interface IObjectBuilder {

	/**
	 * 创建对象
	 * @param request	请求对象	
	 * @param model		控制层返回的数据模型
	 * @param suffix	文件后缀
	 * @return
	 */
	public Object build(HttpServletRequest request, Map<String, Object> model, String suffix);
}
