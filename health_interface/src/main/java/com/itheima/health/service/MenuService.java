package com.itheima.health.service;

import com.itheima.health.pojo.Menu;

import java.util.List;

/**
 * @author keyin Liu
 * @date 2019/11/7
 */
public interface MenuService {
    List<Menu> findChildrenbyparentMenuId(Integer id);
}
