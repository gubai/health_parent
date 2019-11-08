package com.itheima.health.dao;
import com.itheima.health.pojo.Member;
/**
 * @author keyin Liu
 * @date 2019/11/3
 */
public interface MemberDao {

    void add(Member member);

    Member findMemberByTelephone(String telephone);
}
