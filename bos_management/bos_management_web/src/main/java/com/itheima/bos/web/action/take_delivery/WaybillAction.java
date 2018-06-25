package com.itheima.bos.web.action.take_delivery;

import com.itheima.bos.domain.take_delivery.WayBill;
import com.itheima.bos.service.WaybillService;
import com.itheima.bos.web.action.base.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 运单Action
 *
 * @author tengchao
 * @create 2018-01-13-19:16.
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class WaybillAction extends BaseAction<WayBill> {
    @Autowired
    private WaybillService waybillService;
    @Action("waybillAction_save")
    public String save() {
        waybillService.save(model);
        return NONE;
    }
}
