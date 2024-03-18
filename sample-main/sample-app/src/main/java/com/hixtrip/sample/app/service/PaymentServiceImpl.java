package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.PaymentService;
import com.hixtrip.sample.app.convertor.PaymentConvertor;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.model.PaymentInfo;
import com.hixtrip.sample.domain.pay.model.PaymentResult;
import com.hixtrip.sample.domain.dto.ApiResult;
import com.hixtrip.sample.domain.pay.PayDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PayDomainService payDomainService;

    @Override
    public ApiResult<?> paymentRequest(Long orderId, PaymentInfo paymentInfo) {
        //将支付接口返回的支付平台订单号和系统的订单号建立映射关系
        return null;
    }

    @Override
    public ApiResult<?> paymentCallback(CommandPayDTO commandPayDTO) {
        PaymentResult paymentResult = PaymentConvertor.INSTANCE.dtoToResult(commandPayDTO);
        return payDomainService.paymentCallback(paymentResult);
    }
}
