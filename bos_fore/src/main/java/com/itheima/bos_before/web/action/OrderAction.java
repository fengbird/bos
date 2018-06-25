package com.itheima.bos_before.web.action;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.service.OrderService;
import com.itheima.crm.service.Customer;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 订单Action
 *
 * @author tengchao
 * @create 2018-01-09-15:15.
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order>{
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private OrderService orderService;
    private Order order = new Order();
    @Override
    public Order getModel() {
        return order;
    }

    private String sendAreaInfo;
    private String recAreaInfo;

    public void setSendAreaInfo(String sendAreaInfo) {
        this.sendAreaInfo = sendAreaInfo;
    }

    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }

    @Action(value = "orderAction_add",results = {@Result(type = "redirect",location = "/order-success.html")})
    public String add() {
        Area sendArea = new Area();
        //设置寄件人详情信息
        String[] sendInfo = sendAreaInfo.split("/");
        sendArea.setProvince(sendInfo[0]);
        sendArea.setCity(sendInfo[1]);
        sendArea.setDistrict(sendInfo[2]);
        order.setSendArea(sendArea);
        Area recArea = new Area();
        //设置收件人详情信息
        String[] recInfo = recAreaInfo.split("/");
        recArea.setProvince(recInfo[0]);
        recArea.setCity(recInfo[1]);
        recArea.setDistrict(recInfo[2]);
        order.setRecArea(recArea);
        //获取当前登录的用户
        Customer currentCustomer = (Customer) ServletActionContext.getRequest().getSession().getAttribute("currentCustomer");
        if (currentCustomer != null) {
            order.setCustomer_id(currentCustomer.getId());
        }
        //调用webservice服务保存订单信息
        orderService.saveOrder(order);
        return SUCCESS;
    }
}
