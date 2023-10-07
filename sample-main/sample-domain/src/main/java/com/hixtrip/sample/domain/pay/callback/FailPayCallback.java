package com.hixtrip.sample.domain.pay.callback;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

/**
 * 支付失败回调
 */
@Component("failPayCallback")
public class FailPayCallback extends AbstractPayCallback{


    @Override
    protected String doPayCallback(CommandPay commandPay) {

        // 修改订单,修改库存等操作
        return "fail";
    }
}
