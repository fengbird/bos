package com.itheima.bos.service;

import com.itheima.bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author tengchao
 * @create 2018-01-03-19:47.
 */
public interface CourierService {
    void save(Courier courier);

    Page<Courier> queryPage(Courier courier,Pageable pageable);

    void deleteBatch(String ids);

    List<Courier> findAll();
}
