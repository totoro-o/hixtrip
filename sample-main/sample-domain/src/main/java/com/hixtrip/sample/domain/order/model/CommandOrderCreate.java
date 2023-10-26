package com.hixtrip.sample.domain.order.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandOrderCreate {
    private String userId;
    private String skuId;
    private Integer amount;
}
