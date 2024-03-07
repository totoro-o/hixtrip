package com.hixtrip.sample.app.service.strategy;


import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import org.springframework.stereotype.Component;

@Component
public class PayRepeatStrategy implements PayStrategy{
    @Override
    public String pay(CommandPayDTO commandPayDTO) {
        return "已经支付";
    }
}
