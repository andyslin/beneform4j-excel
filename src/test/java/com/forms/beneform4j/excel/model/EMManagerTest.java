package com.forms.beneform4j.excel.model;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forms.beneform4j.excel.model.base.IEM;
import com.forms.beneform4j.excel.model.dynamic.IDynamicTreeEM;
import com.forms.beneform4j.excel.model.tree.ITreeEM;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class EMManagerTest {

    @Test
    public void testLoader() throws Exception {
        IEM em = EMManager.load("testDyanmicResource1");
        IDynamicTreeEM dtem = (IDynamicTreeEM) em;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("name", "name1");
        ITreeEM tem = dtem.apply(param, null);
        System.out.println("test1:" + tem.getName());

        em = EMManager.load("testFile1");
        System.out.println(em);

        em = EMManager.load("testFile2");
        System.out.println(em);

        em = EMManager.load("testFolder1");
        System.out.println(em);

        em = EMManager.load("testFolder2");
        System.out.println(em);

        em = EMManager.load("testResource1");
        System.out.println(em);
    }
}
