package com.itheima.bos.web.action;

import com.alibaba.fastjson.JSONObject;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.StandardService;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

/**
 * @author tengchao
 * @create 2017-12-27-15:50.
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class StandardAction extends BaseAction<Standard>{

    @Autowired
    private StandardService service;
    /**
     * 保存收派标准
     */
    @Action(value = "standardAction_save",results = {@Result(name = "success",type = "redirect",location = "/pages/base/standard.html")})
    public String save() {
        service.save(model);
        return SUCCESS;
    }

    @Action("standardAction_pageQuery")
    public String standardAction_pageQuery() throws IOException {
        PageRequest pageable = new PageRequest(page - 1, rows);
        Page<Standard> standards = service.pageQuery(pageable);
        toJson(standards);
        return NONE;
    }

    @Action("standard_findAll")
    public String standard_findAll() throws IOException {
       List<Standard> standards =  service.findAll();
        String s = JSONObject.toJSONString(standards);
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        ServletActionContext.getResponse().getWriter().print(s);
        return NONE;
    }
}
