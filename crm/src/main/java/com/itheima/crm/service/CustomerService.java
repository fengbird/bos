package com.itheima.crm.service;

import com.itheima.crm.domain.Customer;

import javax.jws.WebService;
import java.util.List;

/**
 * @author tengchao
 * @create 2018-01-07-17:13.
 */
@WebService
public interface CustomerService {
    public List<Customer> findAll();

    //查询未关联到定区的客户信息
    public List<Customer> findCustomerListNotAssociation();

    //查询已经关联到指定定区的客户信息
    public List<Customer> findCustomerListHasAssociation(String decidedzoneId);

    //将客户关联到定区
    public void assignCustomersToDecidedzone(String decidedzoneId, Integer[] customerIds);

    //保存客户
    public void regist(Customer customer);

    //根据电话号码查询客户
    Customer findCustomerByTelephone(String telephone);

    //更新客户类型
    void upateType(String telephone);

    //用户登录
    Customer login(String telephone, String password);

    //根据地址查询fixedAreaId
    String findFixedAreaIdByAddress(String address);
}
