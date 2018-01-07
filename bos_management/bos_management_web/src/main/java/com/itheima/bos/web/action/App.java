package com.itheima.bos.web.action;

import com.itheima.crm.service.Customer;
import com.itheima.crm.service.CustomerService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class App {
    public static void main(String[] args) {
        //加载文件，创建spring工厂对象
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        CustomerService proxy = (CustomerService) ctx.getBean("crmClient");
        List<Customer> all = proxy.findAll();
        System.out.println(all);
    }
}