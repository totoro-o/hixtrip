package com.hixtrip.sample.app.service.strategy;

import com.hixtrip.sample.client.order.dto.CommandPayDTO;

public interface PayStrategy {

    String pay(CommandPayDTO commandPayDTO);
}
