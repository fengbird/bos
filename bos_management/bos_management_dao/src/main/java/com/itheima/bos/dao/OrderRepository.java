package com.itheima.bos.dao;

import com.itheima.bos.domain.take_delivery.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 订单Dao层
 *
 * @author tengchao
 * @create 2018-01-09-15:34.
 */
public interface OrderRepository  extends JpaRepository<Order,Integer>{
}
