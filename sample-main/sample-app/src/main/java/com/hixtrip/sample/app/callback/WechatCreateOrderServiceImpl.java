package com.hixtrip.sample.app.callback;

import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.client.order.dto.WeChatCreateDTO;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.infra.db.dataobject.CommandPayDO;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.handler.BasePayBindService;

// todo 移动到app层去定义
public class WechatCreateOrderServiceImpl extends BasePayBindService<WeChatCreateDTO, CommandPayDO> {


    private OrderConvertor orderConvertor;
    @Override
    public Class<WeChatCreateDTO> getbindParamClass() {
        return WeChatCreateDTO.class;
    }

    @Override
    public CommandPayDO process(WeChatCreateDTO dto) {

        Order order = orderConvertor.commandOrderCreateTOrderDO(dto);

        //todo 实现回调后的处理逻辑
        return null;
    }

    @Override
    public Boolean checkSign(WeChatCreateDTO order) {
        return null;
    }
}
