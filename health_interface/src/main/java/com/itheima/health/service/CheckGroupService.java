package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

/**
 * @author keyin Liu
 * @date 2019/10/26
 */
public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer [] checkitemIds);

    PageResult pageQuerty(Integer currentPage, Integer pageSize, String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckitemList(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    void deleteById(int id);

    List<CheckGroup> findAll();
}
