package com.itheima.health.service;

import com.itheima.health.entity.Result;

import java.util.Map; /**
 * @author keyin Liu
 * @date 2019/11/3
 */
public interface OrderService {
    Result submitOrder(Map map);

    Map findById(Integer id);
}
