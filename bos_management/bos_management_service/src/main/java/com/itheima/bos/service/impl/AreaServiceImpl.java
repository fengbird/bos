package com.itheima.bos.service.impl;

import com.itheima.bos.dao.AreaRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 区域业务层实现类
 *
 * @author tengchao
 * @create 2018-01-05-19:04.
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;
    @Override
    public void saveBatch(List<Area> areas) {
        areaRepository.save(areas);
    }

    @Override
    public Page<Area> queryPage(Area area,PageRequest pageRequest) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("province", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING))
                .withMatcher("city", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING))
                .withMatcher("district", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING));
        Example<Area> areaExample = Example.of(area, exampleMatcher);
        Page<Area> areas = areaRepository.findAll(areaExample,pageRequest);
        return areas;
    }

    @Override
    public List<Area> findAll() {
        List<Area> areas = areaRepository.findAll();
        return areas;
    }
}
