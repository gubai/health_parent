package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

/**
 * @author keyin Liu
 * @date 2019/10/28
 */
public interface SetmealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    Setmeal findById(Integer id);

    List<Integer> findcheckGroupIdsList(int id);


    void edit(Setmeal setmeal, Integer[] checkGroupIds);

    void deleteById(int id);

    List<Setmeal> findAll();



    Setmeal findMobileById (Integer id);
}
