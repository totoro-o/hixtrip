package com.hixtrip.sample.app.api;

import com.hixtrip.sample.domain.dto.ApiResult;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;

/**
 * 订单的service层
 */
public interface OrderService {

    //生成订单
    ApiResult<?> create(CommandOderCreateDTO commandOderCreateDTO);

}
