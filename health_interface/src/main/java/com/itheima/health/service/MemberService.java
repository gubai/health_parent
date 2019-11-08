package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Member;

/**
 * @author keyin Liu
 * @date 2019/11/3
 */
public interface MemberService {

    Member findMemberByTelephone(String telephone);

    void addMember(Member member);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);
}
