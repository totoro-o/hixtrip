package com.hixtrip.sample.app.callback;

import com.hixtrip.sample.client.order.dto.WeChatCreateDTO;
import com.hixtrip.sample.client.order.dto.WechatCallbackDTO;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.handler.BasePayBindService;

public class WechatCallbackServiceImpl extends BasePayBindService<WechatCallbackDTO, CommandPay> {
    @Override
    public Class<WechatCallbackDTO> getbindParamClass() {
        return WechatCallbackDTO.class;
    }

    @Override
    public CommandPay process(WechatCallbackDTO order) {

        //todo 处理微信支付回调逻辑
        return CommandPay.builder().build();
    }

    @Override
    public Boolean checkSign(WechatCallbackDTO order) {
        return true;
    }
}
