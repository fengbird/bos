package com.itheima.bos.service.impl;

import com.itheima.bos.dao.StandardRepository;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tengchao
 * @create 2017-12-27-15:56.
 */
@Service
@Transactional
public class StandardServiceImpl implements StandardService {
    @Autowired
    private StandardRepository standardRepository;
    @Override
    public void save(Standard model) {
        standardRepository.save(model);
    }

    @Override
    public Page<Standard> pageQuery(PageRequest pageable) {
        Page<Standard> standards = standardRepository.findAll(pageable);
        return standards;
    }

    @Override
    public List<Standard> findAll() {
        List<Standard> standards = standardRepository.findAll();
        return standards;
    }
}
