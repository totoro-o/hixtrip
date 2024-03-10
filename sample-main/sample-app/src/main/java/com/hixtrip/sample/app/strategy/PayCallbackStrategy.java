package com.hixtrip.sample.app.strategy;

import com.hixtrip.sample.client.order.vo.PayCallbaskVO;
import com.hixtrip.sample.domain.pay.model.CommandPay;

public interface PayCallbackStrategy {
    PayCallbaskVO payCallback(CommandPay commandPay);
}
