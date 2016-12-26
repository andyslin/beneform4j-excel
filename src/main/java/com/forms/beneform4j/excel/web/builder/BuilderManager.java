package com.forms.beneform4j.excel.web.builder;

import java.util.HashMap;
import java.util.Map;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.web.builder.impl.XlsxDataObjectBuilder;


/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 构建器管理<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2013-10-6<br>
 */
public class BuilderManager{
	
	// 对象创建器
	private static Map<String, Class<? extends IObjectBuilder>> objectBuilders = new HashMap<String, Class<? extends IObjectBuilder>>();
	// 数据文件创建器
	private static Map<String, Class<? extends IDataObjectBuilder>> dataObjectbuilders = new HashMap<String, Class<? extends IDataObjectBuilder>>();
	
	static{
		registerDataObjectBuilder("xlsx", XlsxDataObjectBuilder.class);
	}
	
	/**
	 * 注册下载对象生成器
	 * @param buildType
	 * @param cls
	 */
	public static <E extends IObjectBuilder> void registerObjectBuilder(String buildType, Class<E> cls){
		objectBuilders.put(buildType, cls);
	}
	
	/**
	 * 根据构建类型获取下载对象生成器，没有找到就抛出异常
	 * @param buildType 构建类型
	 * @return
	 */
	public static IObjectBuilder getObjectBuilder(String buildType){
		Class<? extends IObjectBuilder> cls = objectBuilders.get(buildType);
		if(null == cls){
			Throw.throwRuntimeException("未找到和构建类型"+buildType+"对应的下载对象生成器");
		}
		try {
			return cls.newInstance();
		} catch (Exception e) {
			throw Throw.createRuntimeException("创建下载对象生成器失败", e);
		}
	}
	
	/**
	 * 注册数据文件生成器的实现类
	 * @param suffix 后缀
	 * @param cls    实现类
	 */
	public static <E extends IDataObjectBuilder> void registerDataObjectBuilder(String suffix, Class<E> cls){
		dataObjectbuilders.put(suffix.toLowerCase(), cls);
	}
	
	/**
	 * 根据后缀获取数据文件生成器，没有找到就抛出异常
	 * @param suffix
	 * @return
	 */
	public static IDataObjectBuilder getDataObjectBuilder(String suffix){
		Class<? extends IDataObjectBuilder> cls = dataObjectbuilders.get(suffix.toLowerCase());
		if(null == cls){
			Throw.throwRuntimeException("未找到和后缀"+suffix+"对应的数据文件生成器");
		}
		try {
			return cls.newInstance();
		} catch (Exception e) {
			throw Throw.createRuntimeException("创建数据文件生成器失败", e);
		}
	}
}
