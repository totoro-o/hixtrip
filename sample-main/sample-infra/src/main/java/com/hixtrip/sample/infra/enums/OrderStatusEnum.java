package com.hixtrip.sample.infra.enums;

import lombok.extern.slf4j.Slf4j;

/**
 * @program: crowdsourcing
 * @description: 订单状态
 * @author: wb.chaijundong01
 * @create: 2024/3/12
 **/
@Slf4j
public enum OrderStatusEnum {

    WAITING_PAY(1, "待支付"),
    WAITING_SHIPMENT(2, "待发货"),
    SHIPPED(3, "已发货"),
    COMPLETED(4, "已完成"),
    CLOSED(5, "已关闭"),

    ;

    private int value;

    private String desc;

    OrderStatusEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
