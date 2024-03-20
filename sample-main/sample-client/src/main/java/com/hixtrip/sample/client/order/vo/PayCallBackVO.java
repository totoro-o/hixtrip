package com.hixtrip.sample.client.order.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayCallBackVO {

    private Long orderId;

    private String payStatus;

}
