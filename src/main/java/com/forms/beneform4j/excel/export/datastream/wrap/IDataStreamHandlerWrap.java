package com.forms.beneform4j.excel.export.datastream.wrap;

import com.forms.beneform4j.excel.export.datastream.handler.IDataStreamHandler;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 大数据量处理包装接口，将数据处理现场包装起来，实现延后处理功能<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2013-10-2<br>
 */
public interface IDataStreamHandlerWrap {
	
	/**
	 * 处理
	 * @return
	 */
	public Object handler(IDataStreamHandler handler);

}
