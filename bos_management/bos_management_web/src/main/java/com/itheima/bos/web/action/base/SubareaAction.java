package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.SubareaService;
import com.itheima.bos.utils.PropertyOperator;
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

/**
 * 分区Action
 *
 * @author tengchao
 * @create 2018-01-06-12:09.
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<SubArea> {
    @Autowired
    private SubareaService subareaService;

    //分页查询数据
    @Action("subarea_queryPage")
    public String queryPage() {
        PropertyOperator propertyOperator = new PropertyOperator();
        model = propertyOperator.replacePropertyFromEmptyToNull(model);
        Page<SubArea> subAreas = subareaService.queryPage(model,new PageRequest(page-1,rows));
        try {
            toJson(subAreas,"subareas","fixedArea");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    //保存数据
    @Action(value = "subareaAction_save",results = {@Result(type = "redirect",location = "/pages/base/sub_area.html")})
    public String save() {
        subareaService.save(model);
        return SUCCESS;
    }
}
