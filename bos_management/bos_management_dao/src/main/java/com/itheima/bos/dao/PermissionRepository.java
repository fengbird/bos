package com.itheima.bos.dao;

import com.itheima.bos.domain.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tengchao
 * @create 2018-01-28-20:47.
 */
public interface PermissionRepository extends JpaRepository<Permission,Integer> {
}
