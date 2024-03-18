package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.vo.OrderVo;
import com.hixtrip.sample.client.user.dto.UserDTO;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * 订单的service层
 */
public interface OrderService {

    OrderVo createOrder(UserDTO userDTO, CommandOderCreateDTO commandOderCreateDTO);

    void orderPaySuccess(CommandPay commandPay);

    void orderPayFail(CommandPay commandPay);

}
