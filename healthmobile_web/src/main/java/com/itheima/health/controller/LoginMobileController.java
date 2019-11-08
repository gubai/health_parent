package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author keyin Liu
 * @date 2019/11/5
 */
@RestController
@RequestMapping("/login")
public class LoginMobileController {
    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;

    // 使用手机号和验证码登录
    @RequestMapping("/check")
    public Result check(HttpServletResponse response, @RequestBody Map map) {
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");

        //1：从Redis中获取缓存的验证码，判断验证码输入是否正确
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if (codeInRedis == null || !codeInRedis.equals(validateCode)) {
            // 验证码输入错误
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        } else {
            // 验证码输入正确
            // 2：判断当前用户是否为会员
            Member member = memberService.findMemberByTelephone(telephone);
            if (member == null) {
             // 当前用户不是会员，自动完成注册
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.addMember(member);
            }
            // 3：:登录成功
            // 写入Cookie，跟踪用户，用于分布式系统单点登录
            Cookie cookie = new Cookie("login_member_telephone", telephone);
            cookie.setPath("/");// 有效路径
            cookie.setMaxAge(30*24*60*60);// 有效时间
            response.addCookie(cookie);
            return new Result(true,MessageConstant.LOGIN_SUCCESS);
        }

    }
}






