package com.hixtrip.sample.app.callback;

import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.client.order.dto.WeChatCreateDTO;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.handler.BasePayBindService;

public class WechatCreateOrderServiceImpl extends BasePayBindService<WeChatCreateDTO, CommandPay> {


    private OrderConvertor orderConvertor;
    @Override
    public Class<WeChatCreateDTO> getbindParamClass() {
        return WeChatCreateDTO.class;
    }

    @Override
    public CommandPay process(WeChatCreateDTO dto) {

        Order order = orderConvertor.commandOrderCreateTOrderDO(dto);

        //todo //处理微信支付逻辑
        return null;
    }

    @Override
    public Boolean checkSign(WeChatCreateDTO order) {
        return null;
    }
}
