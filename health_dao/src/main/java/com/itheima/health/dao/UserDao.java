package com.itheima.health.dao;

import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.User;

import java.util.List;

/**
 * @author keyin Liu
 * @date 2019/11/3
 */
public interface UserDao {

    User findUserByUsername(String username);

}