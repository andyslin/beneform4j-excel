package com.forms.beneform4j.excel.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forms.beneform4j.excel.model.EMManager;
import com.forms.beneform4j.excel.model.base.IEM;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class EMManagerTest {

    @Test
    public void testLoader() throws Exception {
        IEM em = EMManager.load("testFile1");
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
