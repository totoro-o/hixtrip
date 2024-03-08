package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.common.ResultVo;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;

import java.math.BigDecimal;

/**
 * 订单的service层
 */
public interface OrderService {


    BigDecimal getSkuPrice(String skuId);

    ResultVo createOrder(CommandOderCreateDTO commandOderCreateDTO);

    ResultVo payCallback(CommandPayDTO commandPayDTO);
}
