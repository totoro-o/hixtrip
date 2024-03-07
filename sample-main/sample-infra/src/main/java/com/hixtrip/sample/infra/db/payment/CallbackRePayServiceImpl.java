package com.hixtrip.sample.infra.db.payment;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

@Component
public class CallbackRePayServiceImpl implements CallbackService {

    @Override
    public Boolean pay(CommandPay commandPay) {
        //重复支付逻辑。。
        System.out.println("重复支付");
        return true;
    }

    @Override
    public String getPayStatus() {
        return "rePay";
    }
}
