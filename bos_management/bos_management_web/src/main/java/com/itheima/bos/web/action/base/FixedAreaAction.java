package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.service.FixedAreaService;
import com.itheima.bos.utils.PropertyOperator;
import com.itheima.crm.service.Customer;
import com.itheima.crm.service.CustomerService;
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
 * 定区Action
 *
 * @author tengchao
 * @create 2018-01-06-13:53.
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea> {
    @Autowired
    private FixedAreaService fixedAreaService;
    @Autowired
    private CustomerService crmClient;

    //分页多条件查询所有数据
    @Action("fixedAreaAction_pageQuery")
    public String pageQuery() {
        PropertyOperator propertyOperator = new PropertyOperator();
        model = propertyOperator.replacePropertyFromEmptyToNull(model);
        Page<FixedArea> fixedAreas = fixedAreaService.pageQuery(model, new PageRequest(page - 1, rows));
        try {
            toJson(fixedAreas, "subareas", "couriers");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    //保存定区数据
    @Action(value = "fixedAreaAction_save", results = {@Result(type = "redirect", location = "/pages/base/fixed_area.html")})
    public String save() {
        fixedAreaService.save(model);
        return SUCCESS;
    }

    private Integer courierId;
    private Integer takeTimeId;

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    public void setTakeTimeId(Integer takeTimeId) {
        this.takeTimeId = takeTimeId;
    }
    //关联快递员
    @Action(value = "fixedAreaAction_associationCourierToFixedArea",results = {@Result(type = "redirect",location = "/pages/base/fixed_area.html")})
    public String associationCourierToFixedArea() {
        fixedAreaService.associationCourierToFixedArea(model, courierId, takeTimeId);
        return SUCCESS;
    }

    //查询未关联的客户列表
    @Action("fixedareaAction_findCustomerListNotAssociation")
    public String findCustomerListNotAssociation()  {
        List<Customer> list = crmClient.findCustomerListNotAssociation();
        try {
            this.toJson(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    //查询已关联的客户列表
    @Action("fixedareaAction_findCustomerListHasAssociation")
    public String findCustomerListHasAssociation() {
        List<Customer> list = crmClient.findCustomerListHasAssociation(model.getId());
        try {
            toJson(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    //关联客户
    private List<Integer> customerIds;

    public void setCustomerIds(List<Integer> customerIds) {
        this.customerIds = customerIds;
    }

    @Action(value = "fixedAreaAction_assignCustomers2FixedArea",results = {@Result(type = "redirect",location = "/pages/base/fixed_area.html")})
    public String assignCustomers2FixedArea() {
        crmClient.assignCustomersToDecidedzone(model.getId(),customerIds);
        return SUCCESS;
    }
}
