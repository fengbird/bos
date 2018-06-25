package com.itheima.bos.service.impl;

import com.itheima.bos.dao.PromotionRepository;
import com.itheima.bos.domain.take_delivery.Promotion;
import com.itheima.bos.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 宣传任务业务层实现类
 *
 * @author tengchao
 * @create 2018-01-27-14:13.
 */
@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;
    @Override
    public void save(Promotion promotion) {
        promotionRepository.save(promotion);
    }

    @Override
    public Page<Promotion> pageQuery(PageRequest pageRequest) {
        return promotionRepository.findAll(pageRequest);
    }
}
