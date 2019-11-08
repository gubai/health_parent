package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Setmeal;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map; /**
 * @author keyin Liu
 * @date 2019/10/28
 */

@Repository
public interface SetmealDao {
    void add(Setmeal setmeal);

    void SetmealAndCheckGroup(Map<String, Object> map);

    Page<Setmeal> findPage(String queryString);

    Setmeal findById(Integer id);

    List<Integer> findcheckGroupIdsList(int id);

    void edit(Setmeal setmeal);

    void deleteSetmealAndCheckGroupBysetmealById(Integer id);

    void deleteSetmealById(int id);

    List<Setmeal> findAll();

    Setmeal findMobileById (Integer id);
}
