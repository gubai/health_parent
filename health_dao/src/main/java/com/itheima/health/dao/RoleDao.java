package com.itheima.health.dao;


import com.itheima.health.pojo.Role;
import java.util.Set;

/**
 * @author keyin Liu
 * @date 2019/11/3
 */
public interface RoleDao {

    Set<Role> findRolesByUserId(Integer userId);
}
