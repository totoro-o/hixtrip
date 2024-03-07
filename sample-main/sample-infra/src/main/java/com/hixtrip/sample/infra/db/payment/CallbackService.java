package com.hixtrip.sample.infra.db.payment;

import com.hixtrip.sample.domain.pay.model.CommandPay;

public interface CallbackService {
    Boolean pay(CommandPay commandPay);

    String getPayStatus();
}
