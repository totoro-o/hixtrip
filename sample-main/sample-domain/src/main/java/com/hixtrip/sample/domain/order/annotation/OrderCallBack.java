package com.hixtrip.sample.domain.order.annotation;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
public @interface OrderCallBack {

    CallBackStatus value() default CallBackStatus.SUCCESS;
}
