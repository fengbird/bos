package com.itheima.bos.service;

import com.itheima.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author tengchao
 * @create 2017-12-27-15:55.
 */
public interface StandardService {

    void save(Standard model);

    Page<Standard> pageQuery(PageRequest pageable);

    List<Standard> findAll();
}
