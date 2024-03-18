package com.hixtrip.sample.domain.pay.repository;

import com.hixtrip.sample.domain.dto.ApiResult;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.PaymentResult;

public interface PaymentRepository {

    ApiResult<?> paymentCallback(PaymentResult paymentResult);

}
