package com.forms.beneform4j.excel.export.datastream.handler;

import java.util.List;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 大数据量处理接口<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2013-10-2<br>
 */
public interface IDataStreamHandler {

	/**
	 * 获取处理结果
	 * @return
	 */
	public Object getResult();
	
	/**
	 * 处理当前批次数据
	 * @param datas      当前批次的数据集
	 * @param startIndex 当前批次的开始索引
	 * @param batchSeqno 当前批次序号
	 */
	public void handler(List<Object> datas, long startIndex, int batchSeqno);
}
