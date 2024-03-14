package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;

/**
 * 订单的service层
 */
public interface OrderService {


    void createOrder(CommandOderCreateDTO sampleReq);

    public String payCallback(CommandPayDTO commandPayDTO);

}
