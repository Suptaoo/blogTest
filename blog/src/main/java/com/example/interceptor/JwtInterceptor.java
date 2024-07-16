package com.example.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import co
import com.auth0.jwt.algorithms.Algorithm;
import com.example.config.AuthAccess;
import com.example.domain.User;
import com.example.exception.ServiceException;
import com.example.mapper.UserMapper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Resource
      private UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token =request.getHeader("token");
        if (StrUtil.isBlank(token)){
            token=request.getParameter("token");
        }
        
        if (handler instanceof HandlerMethod){
            AuthAccess authAccess = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            if (authAccess!=null){
                return  true;
            }
        }

        if (StrUtil.isBlank(token)){
            throw  new ServiceException("401","请登录");
        }

        String userId;
        try {
            userId= JWT.decode(token).getAudience().get(0);  //对第一项进行解码
        }catch (JWTException e){
          throw  new ServiceException("401","请登录");
        }

        User user = userMapper.selectByPrimaryKey(Integer.valueOf(userId));
        if (user==null){
            throw new ServiceException("401","请登录");
        }
        //生成Jwt
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token); //验证token
        }catch (Exception e ){
            throw  new ServiceException("401","请登录");
        }
        return  true;
//        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
