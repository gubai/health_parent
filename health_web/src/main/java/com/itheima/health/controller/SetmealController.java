package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author keyin Liu
 * @date 2019/10/28
 */

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;

    //套餐图片的上传
    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile){
        try {
            //之前的文件名
            String originalFilename = imgFile.getOriginalFilename();
            //使用UUID随机产生文件名称，并获取原始文件后缀名,形成一个新文件
            String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
            //七牛云上传
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName );
            //图片上传成功
            Result result = new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
            //将上传图片名称存入Redis,基于Redies的Set集合
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            //图片上传失败
            return new Result(true,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    //新增套餐
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {

        try {
            setmealService.add(setmeal, checkgroupIds);
        } catch (Exception e) {
            //新增失败
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }

        //新增成功
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);


    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = setmealService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }


    //============================

    // 编辑套餐回显时:根据套餐id,进行套餐的数据回显查询
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        Setmeal setmeal = setmealService.findById(id);

        if (setmeal != null) {
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);

        } else {
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }

    }


    // 编辑套餐回显时:根据编辑套餐id,查询检查组的id集合,返回List<Interger>
    @RequestMapping("/findcheckGroupIdsList")
    public List<Integer> findcheckGroupIdsList(int id) {
        List<Integer> list = setmealService.findcheckGroupIdsList(id);
        return list;
    }


    // 编辑套餐保存
    @RequestMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal, Integer[] checkGroupIds) {

        try {
            setmealService.edit(setmeal, checkGroupIds);
        } catch (Exception e) {
            //编辑失败
            return new Result(false, MessageConstant.EDIT_MEMBER_SUCCESS);
        }
        //编辑成功
        return new Result(true, MessageConstant.EDIT_MEMBER_FAIL);


    }

    //删除检查组
    @RequestMapping("/deleteById")
    public Result deleteById(int id) {

        try {
            setmealService.deleteById(id);

        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_MEMBER_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_MEMBER_SUCCESS);
    }


/*    //查询套餐页面下的所有检查组
    @RequestMapping("/findSetmealList")
    public Result findCheckGroupList() {
        List<Setmeal> list = setmealService.findAll();
        if (list != null && list.size() > 0) {
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, list);
        } else {
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }*/



}