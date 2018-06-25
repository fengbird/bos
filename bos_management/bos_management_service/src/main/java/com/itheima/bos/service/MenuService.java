package com.itheima.bos.service;

import com.itheima.bos.domain.system.Menu;

import java.util.List;

/**
 * @author tengchao
 * @create 2018-01-28-19:21.
 */
public interface MenuService {
    List<Menu> list();
    void save(Menu model);
}
