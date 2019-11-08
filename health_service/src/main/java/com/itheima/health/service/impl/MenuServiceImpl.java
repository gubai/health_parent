package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MenuDao;
import com.itheima.health.dao.UserDao;
import com.itheima.health.pojo.Menu;
import com.itheima.health.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author keyin Liu
 * @date 2019/11/7
 */
@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService{
    //用户
    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> findChildrenbyparentMenuId(Integer id) {
        return menuDao.findChildrenbyparentMenuId(id);
    }
}
