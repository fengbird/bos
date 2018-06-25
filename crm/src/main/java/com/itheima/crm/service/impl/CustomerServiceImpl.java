package com.itheima.crm.service.impl;

import com.itheima.crm.dao.CustomerRepository;
import com.itheima.crm.domain.Customer;
import com.itheima.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 客户业务层实现类
 *
 * @author tengchao
 * @create 2018-01-07-17:14.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{
        @Autowired
        private CustomerRepository customerRepository;

        @Override
        public List<Customer> findAll() {
            return customerRepository.findAll();
        }

        @Override
        public List<Customer> findCustomerListNotAssociation() {
            return customerRepository.findByFixedAreaIdIsNull();
        }

        @Override
        public List<Customer> findCustomerListHasAssociation(String fixedAreaId) {
            return customerRepository.findByFixedAreaId(fixedAreaId);
        }

        @Override
        public void assignCustomersToDecidedzone(String fixedAreaId, Integer[] customerIds) {
            customerRepository.updateFixedAreaIdNull(fixedAreaId);
            for (Integer id : customerIds) {
                customerRepository.assiginCustomersToFixedArea(fixedAreaId, id);
            }
        }

        @Override
        public void regist(Customer customer) {
            customerRepository.save(customer);
        }

        @Override
        public Customer findCustomerByTelephone(String telephone) {
            Customer customer = customerRepository.findCustomerByTelephone(telephone);
            return customer;
        }

        @Override
        public void upateType(String telephone) {
            customerRepository.updateTypeByTelephone(telephone);
        }

        @Override
        public Customer login(String telephone, String password) {
            return customerRepository.findCustomerByTelephoneAndPassword(telephone,password);
        }

        @Override
        public String findFixedAreaIdByAddress(String address) {
        return customerRepository.findFixedAreaIdByAddress(address);
    }
}
