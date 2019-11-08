package com.itheima.health.service;

import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Member;

import java.util.Map;

/**
 * @author keyin Liu
 * @date 2019/11/3
 */
public interface MemberService {

    Member findMemberByTelephone(String telephone);

    void addMember(Member member);
}
