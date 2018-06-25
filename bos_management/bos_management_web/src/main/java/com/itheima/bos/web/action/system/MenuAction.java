package com.itheima.bos.web.action.system;

import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.service.MenuService;
import com.itheima.bos.web.action.base.BaseAction;
import lombok.extern.slf4j.Slf4j;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

/**
 * 菜单Action
 *
 * @author tengchao
 * @create 2018-01-28-19:18.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Slf4j
public class MenuAction extends BaseAction<Menu> {
    @Autowired
    private MenuService menuService;
    @Action("menuAction_list")
    public String menuAction_list() {
        /*//查看session中存储的内容以校验shiro框架对session的管理
        HttpSession session = ServletActionContext.getRequest().getSession();
        Enumeration attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String key = (String) attributeNames.nextElement();
            log.info("session中已经存在的键值对有："+key+"::::"+session.getAttribute(key));
        }*/
        List<Menu> list = menuService.list();
        try {
            this.toJson(list,"roles","parentMenu");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    @Action(value = "menuAction_save",results = {
            @Result(location = "/pages/system/menu.html",type = "redirect")})
    public String save() {
        menuService.save(model);
        return SUCCESS;
    }

}
