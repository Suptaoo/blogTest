package com.example.controller;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.config.AuthAccess;
import com.example.config.Result;
import com.example.domain.User;
import com.example.exception.ServiceException;
import com.example.service.UserService;
import com.example.utils.TokenUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@RequestMapping("api/auth")
public class UserController {

    @Resource
    UserService userService;

    @AuthAccess //加上此注解直接绕过拦截器
    @RequestMapping(value = "register" ,method = RequestMethod.POST)
    public Result register(User user)  {

        if (  StrUtil.isBlank(user.getUsername())  || StrUtil.isBlank(user.getPassword())) {
            throw  new ServiceException("账号密码不能为空");
        }
        try{
            user.setPassword(Algorithm.HMAC256(user.getPassword()).toString());
            userService .insert(user);
        }catch (Exception e ){
            throw  new ServiceException(  e.getMessage());
        }
        return  new Result("200","注册成功","");
    }
    @AuthAccess //加上此注解直接绕过拦截器
    @RequestMapping(value  ="login" ,method = RequestMethod.POST)
    public Result login(String username ,String password){
        String token;
         if (StrUtil.isBlank(username) || StrUtil .isBlank( password)){
             throw  new ServiceException("用户名以及密码不能为空");
         }
         User user  =userService .selectByUsername( username);
         if ( !user.getPassword() .equals(Algorithm.HMAC256(user.getPassword()).toString())){
             return  new Result("300","密码错误","");
         }
         token = TokenUtils.createToken(user.getUserId(),user.getPassword());
         user.setToken(token);
        return  new Result("200","登陆成功",user.toString());
    }

    @RequestMapping( value = "me"  ,method = RequestMethod.GET)
    public User  me (){
        return  TokenUtils.getCurrentUser();
    }
}
