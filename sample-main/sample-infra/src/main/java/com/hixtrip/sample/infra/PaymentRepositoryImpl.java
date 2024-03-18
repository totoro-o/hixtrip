package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.dto.ApiResult;
import com.hixtrip.sample.domain.pay.model.PaymentResult;
import com.hixtrip.sample.domain.pay.repository.PaymentRepository;
import com.hixtrip.sample.infra.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentRepositoryImpl implements PaymentRepository {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ApiResult<?> paymentCallback(PaymentResult paymentResult) {
        return null;
    }
}
