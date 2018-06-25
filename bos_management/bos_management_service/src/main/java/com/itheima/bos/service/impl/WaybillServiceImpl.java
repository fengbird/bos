package com.itheima.bos.service.impl;

import com.itheima.bos.dao.WayBillRepository;
import com.itheima.bos.domain.take_delivery.WayBill;
import com.itheima.bos.service.WaybillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 运单业务层实现类
 *
 * @author tengchao
 * @create 2018-01-13-19:21.
 */
@Service
@Transactional
public class WaybillServiceImpl implements WaybillService {
    @Autowired
    private WayBillRepository wayBillRepository;
    @Override
    public void save(WayBill wayBill) {
        wayBillRepository.save(wayBill);
    }
}
