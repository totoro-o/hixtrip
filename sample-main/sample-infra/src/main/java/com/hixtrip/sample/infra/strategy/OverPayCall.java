package com.hixtrip.sample.infra.strategy;

import com.hixtrip.sample.domain.order.strategy.IPayCall;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.constant.PayStatusConstant;
import org.springframework.stereotype.Component;

/**
 * 重复支付订单回调
 */
@Component
public class OverPayCall implements IPayCall {

    @Override
    public void payCall(CommandPay commandPay) {
        // TODO 需要处理退款
    }

    @Override
    public String payStatus() {
        return PayStatusConstant.PAY_OVER;
    }
}
