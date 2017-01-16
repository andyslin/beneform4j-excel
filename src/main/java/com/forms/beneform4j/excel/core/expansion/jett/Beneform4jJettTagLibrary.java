package com.forms.beneform4j.excel.core.expansion.jett;

import java.util.HashMap;
import java.util.Map;

import net.sf.jett.tag.JtTagLibrary;
import net.sf.jett.tag.Tag;
import net.sf.jett.tag.TagLibrary;

public class Beneform4jJettTagLibrary implements TagLibrary {

    private static TagLibrary theLibrary = new Beneform4jJettTagLibrary();

    private Map<String, Class<? extends Tag>> myTagMap;

    private Beneform4jJettTagLibrary() {
        myTagMap = new HashMap<String, Class<? extends Tag>>();
        // 加入jett原生标签库
        myTagMap.putAll(JtTagLibrary.getJtTagLibrary().getTagMap());
        // 加入自定义标签
        myTagMap.put("iterator", IteratorTag.class);
    }

    public static TagLibrary getTagLibrary() {
        return theLibrary;
    }

    public Map<String, Class<? extends Tag>> getTagMap() {
        return myTagMap;
    }

}
