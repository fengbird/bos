package com.itheima.bos.service;

import com.itheima.bos.domain.take_delivery.Order;

import javax.jws.WebService;

/**
 * @author tengchao
 * @create 2018-01-09-15:33.
 */
@WebService
public interface OrderService {
    void saveOrder(Order order);
}
