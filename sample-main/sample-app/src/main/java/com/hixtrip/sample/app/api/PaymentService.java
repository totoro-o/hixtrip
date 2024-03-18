package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.model.PaymentInfo;
import com.hixtrip.sample.domain.dto.ApiResult;

/**
 * 支付的service层
 */

public interface PaymentService {

    //支付(模拟,不需要实现,但要处理结果回调)
    ApiResult<?> paymentRequest(Long orderId, PaymentInfo paymentInfo);

    ApiResult<?> paymentCallback(CommandPayDTO commandPayDTO);

}
