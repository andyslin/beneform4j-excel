package com.forms.beneform4j.excel.export.grid;

import java.io.Serializable;
import java.util.List;


/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 上传下载模型行<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2013-10-6<br>
 */
public class Tr implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5534364508788876449L;

	private List<Td> tds = null;	//单元格列表
	
	public Tr() {
		super();
	}
	
	public Tr(List<Td> tds) {
		super();
		this.tds = tds;
	}

	public List<Td> getTds() {
		return tds;
	}

	public void setTds(List<Td> tds) {
		this.tds = tds;
	}
}
