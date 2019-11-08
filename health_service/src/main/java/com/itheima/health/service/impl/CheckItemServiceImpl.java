package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author keyin Liu
 * @date 2019/10/24
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {

        /*采用mybatis的分页插件*/
        //1：完成对分页的初始化工作
        PageHelper.startPage(currentPage,pageSize);
        //2：查询
        Page<CheckItem> page = checkItemDao.findPage(queryString);

        //3:组织PageResult
        return new PageResult(page.getTotal(),page.getResult());

    }

    @Override
    public void delete(Integer id){
        //查询当前检查项是否和检查组关联
        long count = checkItemDao.findCountCheckItemById(id);
        if (count>0){
            //当前检查项被引用,不能被删除
            throw new RuntimeException("当前检查项被引用,不能被删除");

        }

        checkItemDao.deleteByid(id);
    }

    //主键查询
    @Override
    public CheckItem findById(Integer id) {
        CheckItem checkItem = checkItemDao.findById(id);
        return checkItem;
    }

    @Override
    public List<CheckItem> findAll() {

        return checkItemDao.findAll();
    }


    //编辑保存
    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);

    }


}
