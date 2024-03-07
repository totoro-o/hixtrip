package com.hixtrip.sample.app.component;

import org.springframework.stereotype.Component;

@Component
public class WechatPayCallbackStrategy implements PayCallbackStrategy{

    @Override
    public String paySuccess() {
        //返回给厂商返回通知信息
        return "success";
    }

    @Override
    public String payFail() {
        return "fail";
    }

    @Override
    public String repay() {
        return null;
    }
}
