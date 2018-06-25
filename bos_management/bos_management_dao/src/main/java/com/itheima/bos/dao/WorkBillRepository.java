package com.itheima.bos.dao;

import com.itheima.bos.domain.take_delivery.WorkBill;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 工单repository
 *
 * @author tengchao
 * @create 2018-01-11-15:58.
 */
public interface WorkBillRepository extends JpaRepository<WorkBill,Integer>{
}
