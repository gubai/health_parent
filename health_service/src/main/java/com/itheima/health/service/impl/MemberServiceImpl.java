package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.MemberService;
import com.itheima.health.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author keyin Liu
 * @date 2019/11/3
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    // 体检预约订单
    @Autowired
     private MemberDao memberDao;

    //查询是否为会员
    @Override
    public Member findMemberByTelephone(String telephone) {
        return memberDao.findMemberByTelephone(telephone);

    }

    //手机快速登录时,如果不是会员,则注册
    @Override
    public void addMember(Member member) {
        //新增会员的时候,对密码进行加密
        if (member!=null && member.getPassword()!=null){
            member.setPassword(MD5Utils.md5(member.getPassword()));//密文保存到数据库,保证安全
        }
        memberDao.add(member);
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        // 1：完成对分页初始化工作
        PageHelper.startPage(currentPage,pageSize);
        // 2：查询
        Page<CheckItem> page = memberDao.findPage(queryString);
        // 组织PageResult
        return new PageResult(page.getTotal(),page.getResult());
    }
}
