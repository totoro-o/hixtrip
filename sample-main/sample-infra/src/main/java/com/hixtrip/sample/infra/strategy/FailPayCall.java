package com.hixtrip.sample.infra.strategy;

import com.hixtrip.sample.domain.order.strategy.IPayCall;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.constant.PayStatusConstant;
import org.springframework.stereotype.Component;

@Component
public class FailPayCall implements IPayCall {

    @Override
    public void payCall(CommandPay commandPay) {
        // TODO 不需要处理，需要等待订单过期，恢复库存
    }

    public String payStatus(){
        return PayStatusConstant.PAY_FAIL;
    }
}
