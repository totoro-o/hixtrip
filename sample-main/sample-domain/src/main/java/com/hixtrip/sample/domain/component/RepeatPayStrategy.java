package com.hixtrip.sample.domain.component;

import com.hixtrip.sample.common.enums.OrderPayStatusEnum;
import com.hixtrip.sample.common.exception.BizException;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("repeat")
public class RepeatPayStrategy implements PayCallbackStrategy{

    @Override
    public void payCallback(CommandPay commandPay) {
        if (commandPay.getPayStatus().equals(OrderPayStatusEnum.PAY.name())){
            throw new BizException(-1,"订单已经支付！");
        }
    }
}
