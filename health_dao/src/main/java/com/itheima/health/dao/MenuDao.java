package com.itheima.health.dao;

import com.itheima.health.pojo.Menu;

import java.util.List;
import java.util.Set;

/**
 * @author keyin Liu
 * @date 2019/11/3
 */
public interface MenuDao {

    Set<Menu> findMenusByRoleId(Integer roleId);
    List<Menu> findChildrenbyparentMenuId(Integer id);
}