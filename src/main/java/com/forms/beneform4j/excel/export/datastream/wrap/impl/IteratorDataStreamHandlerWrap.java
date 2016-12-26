package com.forms.beneform4j.excel.export.datastream.wrap.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.forms.beneform4j.excel.export.datastream.handler.IDataStreamHandler;
import com.forms.beneform4j.excel.export.datastream.wrap.IDataStreamHandlerWrap;

public class IteratorDataStreamHandlerWrap implements IDataStreamHandlerWrap{
	
	private Iterator<? extends Object> iterator;

	public IteratorDataStreamHandlerWrap() {
	}
	
	public IteratorDataStreamHandlerWrap(Iterator<? extends Object> iterator) {
		this.iterator = iterator;
	}
	
	public IteratorDataStreamHandlerWrap(List<? extends Object> data) {
		if(null != data){
			this.iterator = data.iterator();	
		}
	}

	@Override
	public Object handler(IDataStreamHandler handler) {
		Iterator<? extends Object> iterator = getIterator();
		if(null != iterator){
			List<Object> datas = new ArrayList<Object>();
			for(; iterator.hasNext(); ){
				datas.add(iterator.next());
			}
			handler.handler(datas, 0, 1);
		}
		return handler.getResult();
	}

	public Iterator<? extends Object> getIterator() {
		return iterator;
	}

	public void setIterator(Iterator<? extends Object> iterator) {
		this.iterator = iterator;
	}
}
