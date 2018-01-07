package com.itheima.bos.service.impl;

import com.itheima.bos.dao.CourierRepository;
import com.itheima.bos.dao.FixedAreaRepository;
import com.itheima.bos.dao.TaketimeRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.FixedAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 定区业务层实现类
 *
 * @author tengchao
 * @create 2018-01-06-14:00.
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService{
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private TaketimeRepository taketimeRepository;
    @Override
    public Page<FixedArea> pageQuery(FixedArea model, PageRequest pageRequest) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        Example<FixedArea> fixedAreaExample = Example.of(model, exampleMatcher);
        Page<FixedArea> fixedAreas = fixedAreaRepository.findAll(fixedAreaExample, pageRequest);
        return fixedAreas;
    }

    @Override
    public void save(FixedArea model) {
        fixedAreaRepository.save(model);
    }

    @Override
    public void associationCourierToFixedArea(FixedArea model, Integer courierId, Integer takeTimeId) {
        Courier courier = courierRepository.findOne(courierId);
        TakeTime takeTime = taketimeRepository.findOne(takeTimeId);
        courier.setTakeTime(takeTime);
        FixedArea fixedArea = fixedAreaRepository.findOne(model.getId());
        fixedArea.getCouriers().add(courier);
    }
}
