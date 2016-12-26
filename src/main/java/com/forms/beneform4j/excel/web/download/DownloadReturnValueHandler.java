package com.forms.beneform4j.excel.web.download;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.forms.beneform4j.web.servlet.ServletHelp;

public class DownloadReturnValueHandler implements HandlerMethodReturnValueHandler{

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		try{
			HttpServletRequest request = ServletHelp.getRequest();
			return null != request 
				&& "download".equalsIgnoreCase(request.getParameter("mime"))
				&& !DownloadType.OBJECT.equals(DownloadType.instance(request.getParameter("downloadType")))
				&& DownloadExecutor.getDataStreamReader() != null;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		mavContainer.setViewName("DownloadView");	
	}

}
