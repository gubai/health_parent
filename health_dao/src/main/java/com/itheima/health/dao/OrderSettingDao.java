package com.itheima.health.dao;
import com.itheima.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author keyin Liu
 * @date 2019/10/30
 */
public interface OrderSettingDao {
    long findCountByOrderDate(Date orderDate);

    void add(OrderSetting orderSetting);

    void update(OrderSetting orderSetting);


    List<OrderSetting> getOrderSettingByMonthBetween(Map<String, String> paramsMap);

    OrderSetting findOrderSettingByOrderDate(Date date);

    void updateReservationsAdd1ByOrderDate(Date date);
}
