package com.itheima.bos.service.impl;

import com.itheima.bos.dao.PermissionRepository;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 权限业务层实现类
 *
 * @author tengchao
 * @create 2018-01-28-20:46.
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<Permission> findAll() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions;
    }

    @Override
    public void save(Permission model) {
        permissionRepository.save(model);
    }
}
