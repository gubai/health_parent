package com.itheima.health.service;

import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author keyin Liu
 * @date 2019/10/30
 */
public interface OrderSettingService {
    void addList(List<OrderSetting> list);

    List<Map> getOrderSettingByMonth(String date);


    void updateNumberByOrderDate(OrderSetting orderSetting);
}
