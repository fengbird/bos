package com.itheima.bos.service.impl;

import com.itheima.bos.dao.CourierRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 收派员实现类
 *
 * @author tengchao
 * @create 2018-01-03-19:48.
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService {
    @Autowired
    private CourierRepository courierRepository;
    @Override
    public void save(Courier courier) {
        courierRepository.save(courier);
    }

    @Override
    public Page<Courier> queryPage(Courier courier,Pageable pageable) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("company", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING))
                .withMatcher("standard.name", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING));
        Example<Courier> courierExample = Example.of(courier,exampleMatcher);
        Page<Courier> couriers = courierRepository.findAll(courierExample,pageable);
        return couriers;
    }

    @Override
    public void deleteBatch(String ids) {
        String[] ids_arr = ids.split(",");
        for (String s : ids_arr) {
            courierRepository.updateDeltag(Integer.parseInt(s));
        }
    }

    @Override
    public List<Courier> findAll() {
        List<Courier> couriers = courierRepository.findAll();
        return couriers;
    }
}
