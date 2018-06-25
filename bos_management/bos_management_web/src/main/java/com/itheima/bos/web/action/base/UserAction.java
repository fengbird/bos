package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.system.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

/**
 * 用户Action
 *
 * @author tengchao
 * @create 2018-01-13-21:40.
 */
@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
    //属性驱动，接收页面输入的验证码
    private String validatecode;

    public void setValidatecode(String validatecode) {
        this.validatecode = validatecode;
    }

    @Action(value = "userAction_login",results = {
            @Result(location = "/index.html",type = "redirect"),
            @Result(name = "login",location = "/login.html",type = "redirect")
    })
    public String login() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        //从session中获取验证码
        String session_code = (String) session.getAttribute("validatecode");
        //进行验证码比对
        if (StringUtils.isNotEmpty(session_code) && StringUtils.isNotEmpty(validatecode)
                && session_code.equals(validatecode)) {
            try {
                //验证码比对成功
                //从框架中获取Subject，代表当前用户
                Subject subject = SecurityUtils.getSubject();
                //创建用户名密码令牌
                UsernamePasswordToken token = new UsernamePasswordToken(model.getUsername(), model.getPassword());
                //执行登录
                subject.login(token);
                //登录成功后，获取User
                User principal = (User) subject.getPrincipal();
                //将User存入session
                session.setAttribute("user",principal);
                //返回成功
                return SUCCESS;
            } catch (AuthenticationException e) {
                e.printStackTrace();
                return LOGIN;
            }

        }
        return LOGIN;
    }
}
