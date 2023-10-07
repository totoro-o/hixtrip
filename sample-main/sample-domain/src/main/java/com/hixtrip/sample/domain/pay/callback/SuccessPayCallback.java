package com.hixtrip.sample.domain.pay.callback;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

/**
 * 支付成功回调
 */
@Component("successPayCallback")
public class SuccessPayCallback extends AbstractPayCallback{


    @Override
    protected String doPayCallback(CommandPay commandPay) {

        // 修改订单,修改库存等操作

        return "success";
    }
}
