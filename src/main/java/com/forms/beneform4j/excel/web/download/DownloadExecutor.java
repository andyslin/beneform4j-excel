package com.forms.beneform4j.excel.web.download;

import javax.servlet.http.HttpServletRequest;

import com.forms.beneform4j.core.dao.mybatis.mapper.impl.DataStreamMapperMethodExecutor;
import com.forms.beneform4j.web.servlet.ServletHelp;

public class DownloadExecutor extends DataStreamMapperMethodExecutor{
	
	protected boolean isSupport(){
		try{
			HttpServletRequest request = ServletHelp.getRequest();
			return null != request 
				&& "download".equalsIgnoreCase(request.getParameter("mime"))
				&& !DownloadType.OBJECT.equals(DownloadType.instance(request.getParameter("downloadType")));
		}catch(Exception e){
			return false;
		}
	}
	
	protected int getFetchSize(){
		try{
			HttpServletRequest request = ServletHelp.getRequest();
			return Integer.parseInt(request.getParameter("fetchSize"));
		}catch(Exception e){
			return 1000;
		}
	}
}
