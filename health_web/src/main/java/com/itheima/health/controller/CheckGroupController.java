package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author keyin Liu
 * @date 2019/10/26
 */

/*
 * 检查组管理
 * */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;

    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {

        try {
            checkGroupService.add(checkGroup, checkitemIds);
        } catch (Exception e) {
            //新增失败
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }

        //新增成功
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);


    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkGroupService.pageQuerty(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
        return pageResult;
    }

    //编辑检查组时:根据检查组id,进行检查组的数据回显查询
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        CheckGroup checkGroup = checkGroupService.findById(id);

        if (checkGroup != null) {
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkGroup);

        } else {
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }

    //编辑检查组时:根据检查组id,查询检查项的id集合,返回List<Interger>
    @RequestMapping("/findCheckitemIdsList")
    public List<Integer> findCheckitemList(int id) {
        List<Integer> list = checkGroupService.findCheckitemList(id);
        return list;
    }


    //编辑检查组
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {

        try {
            checkGroupService.edit(checkGroup, checkitemIds);
        } catch (Exception e) {
            //新增失败
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }

        //新增成功
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);


    }

    //删除检查组
    @RequestMapping("/deleteById")
    public Result deleteById(int id){

        try {
            checkGroupService.deleteById(id);

        } catch (Exception e) {
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    //查询所有
    @RequestMapping("/findCheckGroupList")
    public Result findCheckItemList() {
        List<CheckGroup> list = checkGroupService.findAll();
        if (list != null && list.size() > 0) {
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, list);
        } else {
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);

        }
    }

}
