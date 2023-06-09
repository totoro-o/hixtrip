package com.hixtrip.sample.client.order;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class OrderCreateVO {

    private Long orderId;

}
