package com.itheima.bos.service.impl;

import com.itheima.bos.dao.MenuRepository;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 菜单实现类
 *
 * @author tengchao
 * @create 2018-01-28-19:22.
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;
    @Override
    public List<Menu> list() {
        List<Menu> all = menuRepository.findByParentMenuIdIsNull();
        return all;
    }

    @Override
    public void save(Menu model) {
        if (model.getParentMenu() != null && model.getParentMenu().getId() == 0) {
            model.setParentMenu(null);
        }
        menuRepository.save(model);
    }
}
