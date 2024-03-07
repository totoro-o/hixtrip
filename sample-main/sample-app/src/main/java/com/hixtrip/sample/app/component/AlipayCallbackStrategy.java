package com.hixtrip.sample.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlipayCallbackStrategy implements PayCallbackStrategy{

    @Override
    public String paySuccess() {
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
