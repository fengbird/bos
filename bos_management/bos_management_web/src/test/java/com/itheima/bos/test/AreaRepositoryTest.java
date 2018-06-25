package com.itheima.bos.test;

import com.itheima.bos.dao.AreaRepository;
import com.itheima.bos.domain.base.Area;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试类、
 *
 * @author tengchao
 * @create 2018-01-18-15:52.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AreaRepositoryTest {
    @Autowired
    private AreaRepository repository;
    @Test
    public void test(){
        Page<Area> pages = repository.findAll(new PageRequest(0, 30));
        System.out.println(pages.getContent().size());
    }
}
