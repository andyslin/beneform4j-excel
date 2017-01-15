package com.forms.beneform4j.excel.core.data.accessor;

import java.util.Iterator;
import java.util.Map;

public interface IDataAccessor {

    /**
     * 获取单个对象属性值
     * 
     * @param property
     * @return
     */
    public Object value(String property);

    /**
     * 获取单个对象属性值
     * 
     * @param property
     * @param cls
     * @return
     */
    public <T> T value(String property, Class<T> cls);

    /**
     * 设置属性值
     * 
     * @param property
     * @param value
     */
    public void set(String property, Object value);

    /**
     * 添加变量
     * 
     * @param key
     * @param value
     */
    public void addVar(String key, Object value);

    /**
     * 添加一组变量
     * 
     * @param vars
     */
    public void addVars(Map<? extends String, ? extends Object> vars);

    /**
     * 获取迭代对象属性值
     * 
     * @param property
     * @return
     */
    public Iterator<Object> iterator(String property);

    /**
     * 判断条件是否满足
     * 
     * @param property
     * @return
     */
    public boolean match(String condition);

    /**
     * 获取派生提供器
     * 
     * @param property
     * @return
     */
    public IDataAccessor derive(String property);

    /**
     * 获取父提供器
     * 
     * @return
     */
    public IDataAccessor getParent();

    /**
     * 获取Root对象
     * 
     * @return
     */
    public Object getRoot();
}
