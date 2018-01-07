package com.itheima.bos.service;

import com.itheima.bos.domain.base.FixedArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest; /**
 * @author tengchao
 * @create 2018-01-06-13:59.
 */
public interface FixedAreaService {

    Page<FixedArea> pageQuery(FixedArea model, PageRequest pageRequest);

    void save(FixedArea model);

    void associationCourierToFixedArea(FixedArea model, Integer courierId, Integer takeTimeId);
}
