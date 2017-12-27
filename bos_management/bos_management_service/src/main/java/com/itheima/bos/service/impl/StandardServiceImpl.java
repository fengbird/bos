package com.itheima.bos.service.impl;

import com.itheima.bos.dao.StandardDao;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tengchao
 * @create 2017-12-27-15:56.
 */
@Service
public class StandardServiceImpl implements StandardService {
    @Autowired
    private StandardDao standardDao;
    @Override
    public void save(Standard model) {
        standardDao.save(model);
    }
}
