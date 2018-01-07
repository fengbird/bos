package com.itheima.bos.dao;

import com.itheima.bos.domain.base.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author tengchao
 * @create 2018-01-03-19:54.
 */
public interface CourierRepository extends JpaRepository<Courier,Integer>{
    @Query("update Courier set deltag = '1' where id = ?1")
    @Modifying
    void updateDeltag(int i);
}
