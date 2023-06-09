package com.hixtrip.sample.infra.pay.payplatform;

import com.hixtrip.sample.domain.em.EnumPayPlatform;
import com.hixtrip.sample.domain.pay.PayPlatform;
import com.hixtrip.sample.domain.pay.model.Pay;

public class AliPay implements PayPlatform {

    public AliPay(Object params) {

    }

    @Override
    public EnumPayPlatform getEnum() {
        return EnumPayPlatform.ALIPAY;
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
