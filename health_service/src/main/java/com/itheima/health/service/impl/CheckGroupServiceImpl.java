package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author keyin Liu
 * @date 2019/10/26
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService{
    @Autowired
    private CheckGroupDao checkGroupDao;

    //分页查询
    @Override
    public PageResult pageQuerty(Integer currentPage, Integer pageSize, String queryString) {

        //PageHelper插件
        //1:初始化
        PageHelper.startPage(currentPage,pageSize);
        //2:查询
        Page<CheckGroup> page = checkGroupDao.findPage(queryString);

        //3:封装结果
        return new  PageResult(page.getTotal(),page.getResult());
    }


    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {

        //1.组织检查组的表单数据,保存检查组
        checkGroupDao.add(checkGroup);
        //2.遍历页面传过来的checkItemIds数组,向检查组和检查项的中间表中插入数据
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);

    }


    //3.遍历页面传过来的checkItemIds数组,向检查组和检查项的中间表中插入数据
    private void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkitemIds) {
        if (checkitemIds!=null && checkitemIds.length>0){
            for (Integer checkitemId : checkitemIds) {
                //方案二:使用map集合
                Map<String, Object> map = new HashMap<>();
                map.put("checkGroup_id", checkGroupId);
                map.put("checkItem_Id", checkitemId);
                checkGroupDao.addCheckGroupAndCheckItem(map);
            }

        }

    }



    //根据检查组id,查询对应的检查组信息，并回显页面
    @Override
    public CheckGroup findById(Integer id) {
        CheckGroup checkGroup = checkGroupDao.findById(id);
        return checkGroup;
    }

    //根据检查组id,查询对应的检查项复选框，并回显页面
    @Override
    public List<Integer> findCheckitemList(Integer id) {
        List<Integer>  list = checkGroupDao.findCheckitemList(id);
        return list;
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 1：组织检查组的表单数据，使用id，更新检查组
        checkGroupDao.edit(checkGroup);
        //2:使用检查组的id,删除检查组和检查项中间表的数据
        checkGroupDao.deleteCheckGroupAndCheckItemByCheckgroupById(checkGroup.getId());
        //3.遍历页面传递过来的checkItemIds数组,向检查组和检查项的中间表插入数据
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }



    @Override
    public void deleteById(int id) {
        //1:删除检查项和检查组的中间表数据
        checkGroupDao.deleteCheckGroupAndCheckItemByCheckgroupById(id);
        //2:删除检查组数据
        checkGroupDao.deleteCheckGroupById(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

}
