package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author keyin Liu
 * @date 2019/10/26
 */

@Repository
public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void addCheckGroupAndCheckItem(Map<String, Object> map);

    Page<CheckGroup> findPage(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckitemList(Integer id);

    void edit(CheckGroup checkGroup);

    void deleteCheckGroupAndCheckItemByCheckgroupById(Integer id);

    void deleteCheckGroupById(int id);

    List<CheckGroup> findAll();
}
