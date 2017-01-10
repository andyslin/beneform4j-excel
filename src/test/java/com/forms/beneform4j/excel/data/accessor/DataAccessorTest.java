package com.forms.beneform4j.excel.data.accessor;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.forms.beneform4j.excel.data.accessor.impl.OgnlDataAccessor;
import com.forms.beneform4j.excel.data.accessor.impl.SpelDataAccessor;

public class DataAccessorTest {

    @Test
    public void testOnglDataAccessor() {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("aaa", "aaaValue");
        IDataAccessor a = new OgnlDataAccessor(root);
        IDataAccessor d = a.derive("aaa");
        Assert.assertTrue(d.getParent() == a);
        Assert.assertEquals("aaaValue", d.value("#root"));
    }

    @Test
    public void testSpelDataAccessor() {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("aaa", "aaaValue");
        IDataAccessor a = new SpelDataAccessor(root);
        IDataAccessor d = a.derive("aaa");
        Assert.assertTrue(d.getParent() == a);
        Assert.assertEquals("aaaValue", d.value("#root"));
    }
}
