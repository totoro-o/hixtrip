package com.hixtrip.sample.infra.pay.payplatform;

import com.hixtrip.sample.domain.em.EnumPayPlatform;
import com.hixtrip.sample.domain.pay.PayPlatform;

public class PayPlatformContent {

    public static PayPlatform getPayPlatform(EnumPayPlatform platform, Object params) {
        switch (platform) {
            case ALIPAY:
                return new AliPay(params);
            case WECHAT:
                return new WechatPay(params);
            default: {
                throw new RuntimeException("不支持的支付平台");
            }
        }
    }
}
