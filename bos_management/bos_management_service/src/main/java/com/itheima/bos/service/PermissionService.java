package com.itheima.bos.service;

import com.itheima.bos.domain.system.Permission;

import java.util.List;

/**
 * @author tengchao
 * @create 2018-01-28-20:46.
 */
public interface PermissionService {
    List<Permission> findAll();

    void save(Permission model);
}
