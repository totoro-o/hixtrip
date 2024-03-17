package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.order.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 重复支付策略
 */
@AllArgsConstructor
@Component
public class PayDuplicateStrategy extends AbstractPayCallbackStrategy implements PayCallbackStrategy {

    @Override
    public PayStatusEnum getPayStatus() {
        return PayStatusEnum.DUPLICATE;
    }

    @Override
    public void doHandle(CommandPay commandPay) {
        throw new RuntimeException("订单重复支付，操作失败。");
    }

}
