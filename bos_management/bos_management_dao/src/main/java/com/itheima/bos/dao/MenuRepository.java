package com.itheima.bos.dao;

import com.itheima.bos.domain.system.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author tengchao
 * @create 2018-01-28-19:23.
 */
public interface MenuRepository extends JpaRepository<Menu,Integer> {
    List<Menu> findByParentMenuIdIsNull();
}
