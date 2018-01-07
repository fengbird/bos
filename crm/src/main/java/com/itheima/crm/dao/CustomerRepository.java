package com.itheima.crm.dao;

import com.itheima.crm.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author tengchao
 * @create 2018-01-07-17:16.
 */
public interface CustomerRepository extends JpaRepository<Customer,Integer>{
    List<Customer> findByFixedAreaIdIsNull();

    List<Customer> findByFixedAreaId(String fixedAreaId);

    @Query(value = "update Customer set fixedAreaId = null where fixedAreaId = ?1")
    @Modifying
    void updateFixedAreaIdNull(String fixedAreaId);

    @Query(value = "update Customer set fixedAreaId = ?1 where id = ?2")
    @Modifying
    void assiginCustomersToFixedArea(String fixedAreaId, Integer id);
}
