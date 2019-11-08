package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * @author keyin Liu
 * @date 2019/10/24
 */


public interface CheckItemDao {
    public void add(CheckItem checkItem);

    Page<CheckItem> findPage(String queryString);

    long findCountCheckItemById(Integer id);

    void deleteByid(Integer id);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> findAll();

}
