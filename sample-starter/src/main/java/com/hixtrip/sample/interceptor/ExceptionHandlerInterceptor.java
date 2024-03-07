package com.hixtrip.sample.interceptor;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 10:48
 * 全局异常的自定义
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandlerInterceptor {

    @ExceptionHandler
    public String handleCustomException(Throwable e){
        return e.getMessage();
    }
}
