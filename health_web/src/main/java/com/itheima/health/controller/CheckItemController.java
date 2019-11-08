package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itheima.health.pojo.CheckItem;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author keyin Liu
 * @date 2019/10/24
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    //新增
    @RequestMapping("/add")
    @PreAuthorize(value = "hasAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }

        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    //分页查询
    @RequestMapping("/findPage")
    @PreAuthorize(value = "hasAuthority('CHECKITEM_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkItemService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;

    }

    //删除
    @RequestMapping("/delete")
    @PreAuthorize(value = "hasAuthority('CHECKITEM_DELETE')") // 没有权限 403
    public Result delete(Integer id) {
        try {
            checkItemService.delete(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);


    }

    //跳转到检查项编辑页面
    @RequestMapping("/findById")
    public Result findById(int id) {
        CheckItem checkItem = checkItemService.findById(id);
        if (checkItem != null) {
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);

        } else {
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }


    //编辑保存
    @RequestMapping("/edit")@PreAuthorize(value = "hasAuthority('CHECKITEM_EDIT')")

    public Result edit(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.edit(checkItem);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);

    }


    //查询所有
    @RequestMapping("/findCheckItemList")
    public Result findCheckItemList() {
        List<CheckItem> list = checkItemService.findAll();
        if (list != null && list.size() > 0) {
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, list);
        } else {
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);

        }
    }



}
