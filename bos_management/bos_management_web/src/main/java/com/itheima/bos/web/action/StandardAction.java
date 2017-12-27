package com.itheima.bos.web.action;

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.StandardService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @author tengchao
 * @create 2017-12-27-15:50.
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class StandardAction extends ActionSupport implements ModelDriven<Standard>{

    private Standard model = new Standard();
    @Override
    public Standard getModel() {
        return model;
    }

    @Autowired
    private StandardService service;
    /**
     * 保存收派标准
     */
    @Action(value = "standardAction_save",results = {@Result(name = "success",location = "/pages/base/standard.html")})
    public String save() {
        service.save(model);
        return SUCCESS;
    }
}
