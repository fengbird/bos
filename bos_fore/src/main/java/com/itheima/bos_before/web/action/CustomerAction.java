package com.itheima.bos_before.web.action;

import com.itheima.bos_before.util.MailUtils;
import com.itheima.crm.service.Customer;
import com.itheima.crm.service.CustomerService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 客户Action
 *
 * @author tengchao
 * @create 2018-01-08-18:36.
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {
    private Customer customer = new Customer();
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public Customer getModel() {
        return customer;
    }

    //发送验证码
    @Action("customerAction_sendMsg")
    public String sendMsg() {
        String telephone = customer.getTelephone();
        System.out.println(telephone);
        return NONE;
    }

    //注册
    @Action(value = "customerAction_regist",results = {@Result(type = "redirect",location = "/signup-success.html")})
    public String regist() {
        customerService.regist(customer);
        String activeCode = RandomStringUtils.randomNumeric(32);
        redisTemplate.opsForValue().set(customer.getTelephone(),activeCode,24, TimeUnit.HOURS);
        String content = "尊敬的用户您好，请于24小时内，进行邮箱账户绑定，点击下面的地址完成绑定：<a href='"
                + MailUtils.activeUrl+"?telephone="+customer.getTelephone()+"&activeCode="+activeCode
                + "'>速运快递邮箱绑定地址</a>";
        MailUtils.sendMail("速运快递激活邮件",content,customer.getEmail());
        return SUCCESS;
    }

    private String activeCode;

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    //邮箱激活
    @Action(value = "customerAction_activeMail")
    public String activeMail() throws IOException {
        String code = redisTemplate.opsForValue().get(customer.getTelephone());
        if (StringUtils.equals(activeCode, code)) {
            Customer customer1 = customerService.findCustomerByTelephone(customer.getTelephone());
            if (customer1.getType() == null || customer1.getType() != 1) {
                customerService.upateType(customer.getTelephone());
                //已经绑定过
                ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
                ServletActionContext.getResponse().getWriter().print("账号激活成功，点击此链接跳转到登录界面：" +
                        "<a href='http://localhost:8082/bos_fore/login.html'>点我跳转到登录页面</a>");
            }else {
                //已经绑定过
                ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
                ServletActionContext.getResponse().getWriter().print("邮箱已经绑定过，无须重复绑定");
            }
        }else {
            //激活码不正确
            ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
            ServletActionContext.getResponse().getWriter().print("激活码不正确");
        }
        redisTemplate.delete(customer.getTelephone());
        return NONE;
    }

    //用户登录
    @Action(value = "customerAction_login",results = {
            @Result(type = "redirect",location = "/index.html"),
            @Result(type = "redirect",location = "/login.html",name = "login")})
    public String login() {
        Customer customer1 = customerService.login(customer.getTelephone(),customer.getPassword());
        if (customer1 != null) {
            ServletActionContext.getRequest().getSession().setAttribute("currentCustomer",customer1);
            return SUCCESS;
        }else {
            return LOGIN;
        }
    }

}
