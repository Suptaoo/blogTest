package com.example.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private String code;
    private String message;
    private Object data;

    public  static  Result success(){
        return  new Result("200" ,"请求成功" ,null);
    }

    public  static  Result success(Object data){
        return  new Result("200" ,"请求成功" ,data);
    }

    public  static  Result error(String msg){
        return  new Result("400" ,msg ,null);
    }

    public  static  Result error(String code,String msg){
        return  new Result(code ,msg ,null);
    }

    public  static  Result error(){
        return  new Result("500" ,"系统错误" ,null);
    }


}


