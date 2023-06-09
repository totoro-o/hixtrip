package com.hixtrip.sample.domain.pay;

import com.hixtrip.sample.domain.em.EnumPayPlatform;
import com.hixtrip.sample.domain.pay.model.Pay;

public interface PayPlatform {

    EnumPayPlatform getEnum();

    /**
     * 支付是否成功
     * @return
     */
    boolean paySuccess();

    /**
     * 获取支付信息
     * @return
     */
    Pay getPay();

    /**
     * 通知支付平台
     * @return
     */
    Object notifyPlatform();

}
