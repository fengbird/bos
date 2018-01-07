package com.itheima.bos.service.impl;

import com.itheima.bos.dao.TaketimeRepository;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.TaketimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 收派时间业务层实现类
 *
 * @author tengchao
 * @create 2018-01-07-10:23.
 */
@Service
@Transactional
public class TaketimeServiceImpl implements TaketimeService {
    @Autowired
    private TaketimeRepository taketimeRepository;
    @Override
    public List<TakeTime> findAll() {
        List<TakeTime> takeTimes = taketimeRepository.findAll();
        return takeTimes;
    }
}
