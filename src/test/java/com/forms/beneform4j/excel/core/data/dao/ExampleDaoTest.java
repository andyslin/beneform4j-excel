package com.forms.beneform4j.excel.core.data.dao;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forms.beneform4j.core.dao.stream.IListStreamReader;
import com.forms.beneform4j.core.util.init.InitManage;
import com.forms.beneform4j.core.util.track.Tracker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
@Service
public class ExampleDaoTest {

    @Resource
    private IExampleDao dao;

    @Before
    public void init() {
        InitManage.initialize();
        Tracker.start();
    }

    @Test
    public void testSelectListStreamIterator() {
        IListStreamReader<ParamEnumDef> reader = dao.selectListStream("MENU");

        reader.read();
        reader.reset();

        Iterator<ParamEnumDef> i = reader.iterator();
        int index = 0;
        for (; i.hasNext();) {
            System.out.println((++index) + "." + i.next());
        }
    }

    // @Test
    public void testSelectListStream() {
        List<ParamEnumDef> list = null;
        IListStreamReader<ParamEnumDef> reader = dao.selectListStream("MENU");
        int batch = 0;
        System.out.println("###### 1 ");// 第1次测试，调用固定size的方法
        while ((list = reader.read()) != null) {
            System.out.println("第[" + (++batch) + "]批次，共[" + list.size() + "]笔");
        }
        Assert.assertEquals(2, batch);// 当前数据量

        System.out.println("###### 2 ");// 第2次测试，调用可变size的方法，size和固定的相同
        reader = dao.selectListStream("MENU", 2);
        batch = 0;
        while ((list = reader.read()) != null) {
            System.out.println("第[" + (++batch) + "]批次，共[" + list.size() + "]笔");
        }
        Assert.assertEquals(2, batch);// 当前数据量

        System.out.println("###### 3 ");// 第3次测试，调用可变size的方法，size和固定的不同
        reader = dao.selectListStream("MENU", 3);
        batch = 0;
        while ((list = reader.read()) != null) {
            System.out.println("第[" + (++batch) + "]批次，共[" + list.size() + "]笔");
        }
        Assert.assertEquals(1, batch);// 当前数据量
    }

    public void destory() {
        InitManage.destory();
    }
}
