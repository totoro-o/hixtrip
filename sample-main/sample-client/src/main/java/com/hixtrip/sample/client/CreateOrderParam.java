package com.hixtrip.sample.client;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateOrderParam implements Serializable {
    private Long skuId;
    private Long quantity;
}
