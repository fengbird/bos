package com.itheima.bos.service.impl;

import com.itheima.bos.dao.AreaRepository;
import com.itheima.bos.dao.FixedAreaRepository;
import com.itheima.bos.dao.OrderRepository;
import com.itheima.bos.dao.WorkBillRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.domain.take_delivery.WorkBill;
import com.itheima.bos.service.OrderService;
import com.itheima.crm.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * 订单业务层实现类
 *
 * @author tengchao
 * @create 2018-01-09-15:33.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AreaRepository areaRepository;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private CustomerService customerService;
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Autowired
    private WorkBillRepository workBillRepository;
    @Override
    public void saveOrder(Order order) {
        Area sendArea = order.getSendArea();
        Area recArea = order.getRecArea();
        if (sendArea != null) {
            Area absoluteSendArea = areaRepository.findAreaByProvinceAndCityAndDistrict(sendArea.getProvince(),
                    sendArea.getCity(), sendArea.getDistrict());
            order.setSendArea(absoluteSendArea);
        }
        if (recArea != null) {
            Area absoluteRecArea = areaRepository.findAreaByProvinceAndCityAndDistrict(recArea.getProvince(),
                    recArea.getCity(), recArea.getDistrict());
        order.setRecArea(absoluteRecArea);
        }
        //保存订单信息到订单表
        order.setOrderTime(new Date());
        order.setOrderNum(UUID.randomUUID().toString());
        orderRepository.save(order);
        //根据CRM客户地址自动分配快递员
        if (StringUtils.isNotBlank(order.getSendAddress())) {
            String finxedAreaId = customerService.findFixedAreaIdByAddress(order.getSendAddress());
            if (finxedAreaId != null) {
                FixedArea fixedArea = fixedAreaRepository.findOne(finxedAreaId);
                Set<Courier> couriers = fixedArea.getCouriers();
                for (Courier courier : couriers) {
                    //查询到定区id了，可以完成自动分单
                    order.setOrderType("自动分单");
                    order.setCourier(courier);
                    //为快递员产生一个工单
                    WorkBill workBill = new WorkBill();
                    workBill.setAttachbilltimes(0);
                    workBill.setBuildtime(new Date());
                    workBill.setCourier(courier);
                    workBill.setOrder(order);
                    workBill.setPickstate("新单");
                    workBill.setRemark(order.getRemark());
                    workBill.setSmsNumber("123");
                    workBill.setType("新");
                    workBillRepository.save(workBill);
                    System.out.println("工单信息：请到" + order.getSendAddress() + "取件，客户电话：" + order.getSendMobile());
                    return;
                }
            }
        }
        //3.基于分区关键字匹配法实现自动分单
        if (order.getSendArea() != null) {
            //获得指定区域中包含的所有分区
            Set<SubArea> subareas = order.getSendArea().getSubareas();
            for (SubArea subarea : subareas) {
                String keyWords = subarea.getKeyWords();//分区关键字
                String assistKeyWords = subarea.getAssistKeyWords();//辅助关键字
                if (order.getSendAddress().contains(keyWords) || order.getSendAddress().contains(assistKeyWords)) {
                    FixedArea fixedArea = subarea.getFixedArea();
                    Set<Courier> couriers = fixedArea.getCouriers();
                    //根据上下班时间进行匹配，匹配到正在值班的快递员
                    Iterator<Courier> iterator = couriers.iterator();
                    if(iterator.hasNext()){
                        //查询到定区id了，可以完成自动分单
                        order.setOrderType("自动分单");
                        Courier courier = iterator.next();
                        order.setCourier(courier);
                        //为快递员产生一个工单
                        WorkBill workBill = new WorkBill();
                        workBill.setAttachbilltimes(0);
                        workBill.setBuildtime(new Date());
                        workBill.setCourier(courier);
                        workBill.setOrder(order);
                        workBill.setPickstate("新单");
                        workBill.setRemark(order.getRemark());
                        workBill.setSmsNumber("123");
                        workBill.setType("新");
                        workBillRepository.save(workBill);//保存工单
                        //调用短信平台为快递员发送短信
                        System.out.println("工单信息：请到"+order.getSendAddress()+"取件，客户电话："+order.getSendMobile());
                        return ;
                    }
                }
            }
        }
        //5.如果没有完成自动分担，转入人工调度
    }
}
