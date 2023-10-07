package com.hixtrip.sample.domain.pay.callback;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

/**
 * 支付重复回调
 */
@Component("repeatPayCallback")
public class RepeatPayCallback extends AbstractPayCallback{


    @Override
    protected String doPayCallback(CommandPay commandPay) {

        return "repeat";
    }
}
