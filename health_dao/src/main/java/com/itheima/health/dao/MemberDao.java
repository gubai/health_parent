package com.itheima.health.dao;
import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Member;
/**
 * @author keyin Liu
 * @date 2019/11/3
 */
public interface MemberDao {

    void add(Member member);

    Member findMemberByTelephone(String telephone);

    Page<CheckItem> findPage(String queryString);
}
