package com.itheima.bos.service.impl;

import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.dao.SubareaRepository;
import com.itheima.bos.service.SubareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * subarea业务层实现类
 *
 * @author tengchao
 * @create 2018-01-06-12:13.
 */
@Service
@Transactional
public class SubareaServiceImpl implements SubareaService {
    @Autowired
    private SubareaRepository subareaRepository;
    @Override
    public void save(SubArea model) {
        model.setId(UUID.randomUUID().toString());
        subareaRepository.save(model);
    }

    @Override
    public Page<SubArea> queryPage(SubArea model, PageRequest pageRequest) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("area.province", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING))
                .withMatcher("area.city", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING))
                .withMatcher("area.district", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING))
                .withMatcher("keyWords", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING));
        Example<SubArea> subAreaExample = Example.of(model, exampleMatcher);
        Page<SubArea> subAreas = subareaRepository.findAll(subAreaExample, pageRequest);
        return subAreas;
    }
}
