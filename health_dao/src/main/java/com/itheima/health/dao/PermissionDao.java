package com.itheima.health.dao;


import com.itheima.health.pojo.Permission;

import java.util.Set;

/**
 * @author keyin Liu
 * @date 2019/11/3
 */
public interface PermissionDao {

    Set<Permission> findRolesByUserId(Integer roleId);

}
