package com.example.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.domain.User;
import com.example.mapper.UserMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {
    private  static UserMapper StaticUserMapper;
    @Resource
    UserMapper userMapper;
    @PostConstruct
    public  void  setUserService(){
        StaticUserMapper =userMapper;
    }

/*
* 生成token
*
* */

    public static  String createToken(int userId,String sign ){
        return JWT.create().withAudience(String.valueOf(userId))  //将userid保存到token中作为负载
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))//2小时后过期
                .sign(Algorithm.HMAC256(sign));  //密码作为秘钥
    }

    /**
     *
     * 获取当前用户
    * */

    public  static User getCurrentUser(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token =request.getHeader("token");
            if (StrUtil.isBlank(token)){
                String userId=JWT.decode(token).getAudience().get(0);
                return StaticUserMapper.selectByPrimaryKey(Integer.valueOf(userId));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
