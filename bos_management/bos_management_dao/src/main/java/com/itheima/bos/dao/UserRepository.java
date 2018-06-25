package com.itheima.bos.dao;

import com.itheima.bos.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author tengchao
 * @create 2018-01-13-21:38.
 */
public interface UserRepository extends JpaRepository<User,Integer>{
    List<User> findByUsername(String username);
}
