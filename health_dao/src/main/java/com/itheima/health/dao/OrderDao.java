package com.itheima.health.dao;

import com.itheima.health.pojo.Order;
import java.util.List;
import java.util.Map;

/**
 * @author keyin Liu
 * @date 2019/11/3
 */
public interface OrderDao {
    List<Order> findOrderListByCondition(Order orderParams);


    Map findById(Integer id);

    void add(Order order);
}
