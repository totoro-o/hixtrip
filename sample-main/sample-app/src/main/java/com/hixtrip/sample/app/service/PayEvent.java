package com.hixtrip.sample.app.service;

import com.hixtrip.sample.domain.pay.model.CommandPay;

public interface PayEvent {

    void payCallback(CommandPay commandPay);
}
