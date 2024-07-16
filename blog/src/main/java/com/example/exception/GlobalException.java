package com.example.exception;


import com.example.config.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler
    @ResponseBody
    public Result serverException(ServiceException e){
        return Result.error(e.getCode(),e.getMessage());
    }
}
