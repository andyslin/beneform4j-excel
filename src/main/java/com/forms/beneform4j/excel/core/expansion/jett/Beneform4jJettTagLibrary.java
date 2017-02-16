package com.forms.beneform4j.excel.core.expansion.jett;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.ClassUtils;

import net.sf.jett.tag.JtTagLibrary;
import net.sf.jett.tag.Tag;
import net.sf.jett.tag.TagLibrary;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Beneform4j标签库<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class Beneform4jJettTagLibrary implements TagLibrary {

    private static TagLibrary theLibrary = new Beneform4jJettTagLibrary();

    private Map<String, Class<? extends Tag>> myTagMap;

    private Beneform4jJettTagLibrary() {
        myTagMap = new HashMap<String, Class<? extends Tag>>();
        // 加入jett原生标签库
        myTagMap.putAll(JtTagLibrary.getJtTagLibrary().getTagMap());
        // 加入自定义标签
        if (ClassUtils.isPresent("com.forms.beneform4j.core.dao.stream.IListStreamReader", Beneform4jJettTagLibrary.class.getClassLoader())) {
            myTagMap.put("iterator", IteratorTag.class);
        }
    }

    public static TagLibrary getTagLibrary() {
        return theLibrary;
    }

    public Map<String, Class<? extends Tag>> getTagMap() {
        return myTagMap;
    }

}
