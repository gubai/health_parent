package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.MenuService;
import com.itheima.health.service.UserService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Reference
    UserService userService;

    @Reference
    MenuService menuService;

    // 获取用户信息（用户名）
    @RequestMapping(value = "/getUsername")
    public Result getUsername() {
        // 获取用户信息（从SpringSecurity）
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, username);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }

    }

    //通过用户名获取菜单列表
    @RequestMapping(value = "/getMenuListByusername")
    public Result getMenuListByusername() {
        List<Menu> menuList = new ArrayList<>();
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();
            com.itheima.health.pojo.User user1 = userService.findUserByUsername(username);
            Set<Role> roles = user1.getRoles();

            if (roles != null && roles.size() > 0) {

                LinkedHashSet<Menu> menus =null;
                //遍历得到role
                for (Role role : roles) {
                    // 父菜单
                     menus = role.getMenus();
                    //遍历子菜单
                    for (Menu menu : menus) {
                        // id 下一级
                        System.out.println(menu.getId());
                        List<Menu> children = menuService.findChildrenbyparentMenuId(menu.getId());
                        menu.setChildren(children);
                    }
                }

                return new Result(true, MessageConstant.GET_MENU_SUCCESS, menus);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new Result(false, MessageConstant.GET_MENU_FAIL);
    }


}

