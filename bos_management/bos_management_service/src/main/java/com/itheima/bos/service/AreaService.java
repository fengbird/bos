package com.itheima.bos.service;

import com.itheima.bos.domain.base.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List; /**
 * @author tengchao
 * @create 2018-01-05-19:04.
 */
public interface AreaService {
    void saveBatch(List<Area> areas);

    Page<Area> queryPage(Area area,PageRequest pageRequest);

    List<Area> findAll();
}
