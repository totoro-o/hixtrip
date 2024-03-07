package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.common.util.JsonData;

/**
 * 订单的service层
 */
public interface OrderService {


    JsonData order(CommandOderCreateDTO commandOderCreateDTO);

    JsonData payCallback(CommandPayDTO commandPayDTO);
}
