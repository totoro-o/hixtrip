package com.hixtrip.sample.domain.pay.model;

import com.hixtrip.sample.domain.pay.enums.PaymentStatusEnum;
import lombok.Data;

@Data
public class PaymentResult {

    /**
     * @see PaymentStatusEnum
     */
    private Integer paymentStatus;



}
