package com.itheima.health.job;

import com.itheima.health.constant.RedisConstant;
import com.itheima.health.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;
import java.util.Iterator;
import java.util.Set;
/**
 * @author keyin Liu
 * @date 2019/10/30
 */
public class ClearImgJob {
    /*
    * 定时任务:清理垃圾照片
    * */
    @Autowired
    private JedisPool jedisPool;

    public void executeJob(){

        //计算setmealPicResources集合与setmealPicDbResources集合的差值，清理图片
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()){
            String picName = iterator.next();
            System.out.println("删除的图片名称是:" + picName);
            //删除七牛云的图片
            QiniuUtils.deleteFileFromQiniu(picName);
            // 2:：删除key值为setmealPicResources 的redis的数据
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,picName);
        }
    }


}
