package com.itheima.health.service;

import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.User;

import java.util.List;

/**
 * @author keyin Liu
 * @date 2019/11/5
 */
public interface UserService {


    User findUserByUsername(String username);


}
