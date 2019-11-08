package com.itheima.health.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author keyin Liu
 * @date 2019/10/28
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;


    //分页查询
    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        //1:初始化分页参数
        PageHelper.startPage(currentPage, pageSize);
        //2:查询
        Page<Setmeal> page = setmealDao.findPage(queryString);
        //3:组织响应数据
        return new PageResult(page.getTotal(), page.getResult());
    }

    //新增套餐：1-1 ：往套餐表写入数据
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {

        //组织套餐的表单数据,保存套餐的基本信息
        setmealDao.add(setmeal);
        //绑定套餐和检查组的多对多关系
        setSetmealAndCheckGroup(setmeal.getId(), checkgroupIds);
        //将图片名称保存到rediis里面
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());

    }


    //新增套餐：1-2：给套餐和检查组的中间表写入数据
    private void setSetmealAndCheckGroup(Integer setmealId, Integer[] checkgroupIds) {
        if (checkgroupIds != null && checkgroupIds.length > 0) {
        }

        //遍历页面传过来的checkgroupIds数组
        for (Integer checkgroupId : checkgroupIds) {
            //方案二:使用map集合
            Map<String, Object> map = new HashMap<>();
            map.put("setmeal_Id", setmealId);
            map.put("checkGroup_Id", checkgroupId);
            setmealDao.SetmealAndCheckGroup(map);
        }

    }


    // 套餐编辑：1-1: 根据套餐id,查询对应的套餐信息，并回显页面
    @Override
    public Setmeal findById(Integer id) {
        Setmeal setmeal = setmealDao.findById(id);
        return setmeal;
    }


    //套餐编辑：1-2根据套餐id,查询对应的检查项组复选框，并回显页面
    @Override
    public List<Integer> findcheckGroupIdsList(int id) {
        List<Integer> list = setmealDao.findcheckGroupIdsList(id);
        return list;
    }


    //套餐编辑：1-3：编辑保存
    @Override
    public void edit(Setmeal setmeal, Integer[] checkGroupIds) {
        // 1：组织套餐的表单数据，使用id，更新检查组
        setmealDao.edit(setmeal);
        //2:使用套餐id,删除检查组和套餐中间表的数据
        setmealDao.deleteSetmealAndCheckGroupBysetmealById(setmeal.getId());
        //3.遍历页面传递过来的checkGroupIds数组,向套和检查组的中间表插入数据
        setSetmealAndCheckGroup(setmeal.getId(), checkGroupIds);
    }

    @Override
    public void deleteById(int id) {
        //1:删除检查项和检查组的中间表数据
        setmealDao.deleteSetmealAndCheckGroupBysetmealById(id);
        //2:删除检套餐数据
        setmealDao.deleteSetmealById(id);
    }


    //普通查询，把已有的套餐显示到页面
    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }



    // 套餐编辑：1-1: 根据套餐id,查询对应的套餐信息，并回显页面
    @Override
    public Setmeal findMobileById(Integer id) {
        Setmeal setmeal = setmealDao.findMobileById(id);
        return setmeal;
    }

}

