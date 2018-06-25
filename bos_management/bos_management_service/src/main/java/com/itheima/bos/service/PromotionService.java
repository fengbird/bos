package com.itheima.bos.service;

import com.itheima.bos.domain.take_delivery.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 宣传任务业务层接口
 *
 * @author tengchao
 * @create 2018-01-27-14:12.
 */
public interface PromotionService {
    void save(Promotion promotion);

    Page<Promotion> pageQuery(PageRequest pageRequest);
}
