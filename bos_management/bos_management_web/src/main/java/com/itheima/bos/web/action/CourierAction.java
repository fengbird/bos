package com.itheima.bos.web.action;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.CourierService;
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
import java.util.List;

/**
 * 收派员Action类
 *
 * @author tengchao
 * @create 2018-01-03-19:44.
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends BaseAction<Courier> {
    @Autowired
    private CourierService courierService;

    //保存快递员信息
    @Action(value="courierAction_save",results = {@Result(name="success",type = "redirect",location = "/pages/base/courier.html")})
    public String courierAction_save() {
        courierService.save(model);
        return SUCCESS;
    }

    //查询所有快递员信息
    @Action("courierAction_listajax")
    public String listajax() throws IOException {
        List<Courier> couriers = courierService.findAll();
        toJson(couriers,"fixedAreas");
        return NONE;
    }

    //分页查询快递员信息
    @Action("courierAction_queryPage")
    public String courierAction_queryPage() throws IOException {
        PropertyOperator propertyOperator = new PropertyOperator();
        model = propertyOperator.replacePropertyFromEmptyToNull(model);
        Page<Courier> couriers = courierService.queryPage(model,new PageRequest(page - 1, rows));
        toJson(couriers,"fixedAreas");
        return NONE;
    }

    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    //批量删除快递员
    @Action(value = "courierAction_deleteBatch",results = {@Result(name = "success",type = "redirect",location = "/pages/base/courier.html")})
    public String courierAction_deleteBatch() {
        courierService.deleteBatch(ids);
        return SUCCESS;
    }
}
