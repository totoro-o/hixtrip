package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.OrderReq;
import com.hixtrip.sample.util.RespResult;

public interface OrderService {

    /**
     * 创建订单
     *
     * @param orderReq
     * @return
     */
    RespResult createOrder(OrderReq orderReq);


    /***
     * 支付成功修改状态
     */
    int updateAfterPayStatus(String id);
}
