package com.forms.beneform4j.excel.core.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forms.beneform4j.excel.core.model.em.EMManager;
import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.em.dynamic.IDynamicTreeEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEM;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class DynamicEMManagerTest {

    @Test
    public void testLoader() throws Exception {
        new TestThread("test1", "name1").start();
        new TestThread("test2", "name2").start();
        TimeUnit.MINUTES.sleep(10);
    }

    public class TestThread extends Thread {

        private String key;
        private String name;

        private TestThread(String key, String name) {
            this.key = key;
            this.name = name;
        }

        @Override
        public void run() {
            IEM em = EMManager.load("testDyanmicResource1");
            IDynamicTreeEM dtem = (IDynamicTreeEM) em;
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("name", name);
            ITreeEM tem = dtem.apply(param, null);
            System.out.println(key + ":" + tem.getName());
        }

    }
}
