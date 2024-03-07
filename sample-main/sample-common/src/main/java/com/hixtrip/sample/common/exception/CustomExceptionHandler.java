package com.hixtrip.sample.common.exception;


import com.hixtrip.sample.common.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 将 @ControllerAdvice 注解放在类上，您可以创建一个全局的控制器增强器，它会影响到整个应用程序中的所有控制器。
 */
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * 有异常就会进入到这个处理器中
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData handler(Exception e){

        //确认是否自定义异常
        if (e instanceof  BizException){
            BizException bizException = (BizException) e;
            return JsonData.buildCodeAndMsg(bizException.getCode(), bizException.getMsg());
        }else {
            return JsonData.buildError("全局异常，未知错误");
        }

    }
}
