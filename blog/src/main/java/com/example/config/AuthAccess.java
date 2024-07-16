package com.example.config;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthAccess {

    /**
     * 加上此注解可以绕过拦截器  不同获取token也可以访问
     */
}
