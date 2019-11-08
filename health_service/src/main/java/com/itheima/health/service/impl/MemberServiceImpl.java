package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.MemberService;
import com.itheima.health.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
}
