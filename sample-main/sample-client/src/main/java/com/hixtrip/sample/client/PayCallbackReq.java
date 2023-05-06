package com.hixtrip.sample.client;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PayCallbackReq {

    private String orderNo;

    private String serialNumber;

    private String payStatus;

    private LocalDateTime payTime;

    private BigDecimal payAmount;

    private String message;

    private String payType;

    private String mchId;

    private String sign;

    private String signType;
}
