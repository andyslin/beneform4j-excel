package com.forms.beneform4j.excel.export.datastream.handler.impl;

import java.util.ArrayList;
import java.util.List;

import com.forms.beneform4j.excel.export.datastream.handler.IDataStreamHandler;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 返回List的大数据量处理器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2013-10-2<br>
 */
public class SimpleListDataStreamHandler implements IDataStreamHandler {
	
	private final List<Object> list = new ArrayList<Object>();
	
	public Object getResult() {
		return list;
	}

	public void handler(List<Object> datas, long startIndex, int batchSeqno) {
		list.addAll(datas);
	}
}
