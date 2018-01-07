package com.itheima.bos.service;

import com.itheima.bos.domain.base.SubArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @author tengchao
 * @create 2018-01-06-12:12.
 */
public interface SubareaService {
    void save(SubArea model);

    Page<SubArea> queryPage(SubArea model, PageRequest pageRequest);
}
