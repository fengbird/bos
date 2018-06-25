package com.itheima.bos.web.action.system;

import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.service.PermissionService;
import com.itheima.bos.web.action.base.BaseAction;
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
 * 权限Action
 *
 * @author tengchao
 * @create 2018-01-28-20:43.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class PermissionAction extends BaseAction<Permission> {
    @Autowired
    private PermissionService permissionService;

    @Action("permissionAction_list")
    public String list() {
        List<Permission> permissions = permissionService.findAll();
        try {
            this.toJson(permissions,"roles");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    @Action(value = "permissionAction_save",results = {
            @Result(location = "/pages/system/permission.html",type = "redirect")
    })
    public String save() {
        permissionService.save(model);
        return SUCCESS;
    }
}
