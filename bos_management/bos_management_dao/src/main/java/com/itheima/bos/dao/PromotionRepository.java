package com.itheima.bos.dao;

import com.itheima.bos.domain.take_delivery.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tengchao
 * @create 2018-01-27-14:14.
 */
public interface PromotionRepository extends JpaRepository<Promotion,Long> {
}
