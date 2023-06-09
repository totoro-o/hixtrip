package com.hixtrip.sample.infra.pay.payplatform;

import com.hixtrip.sample.domain.em.EnumPayPlatform;
import com.hixtrip.sample.domain.pay.PayPlatform;
import com.hixtrip.sample.domain.pay.model.Pay;

import java.util.Map;

public class WechatPay implements PayPlatform {

    public WechatPay(Object params) {

    }
    @Override
    public EnumPayPlatform getEnum() {
        return EnumPayPlatform.WECHAT;
    }

    @Override
    public boolean paySuccess() {
        return false;
    }

    @Override
    public Pay getPay() {
        return null;
    }

    @Override
    public Object notifyPlatform() {
        return null;
    }


}
