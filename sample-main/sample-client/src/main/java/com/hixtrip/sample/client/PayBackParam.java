package com.hixtrip.sample.client;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayBackParam implements Serializable {
    private Long orderId;
    //还有其他返回的字段
}
