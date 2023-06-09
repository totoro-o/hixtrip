package com.hixtrip.sample.domain.order.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class OrderCreate {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 购物车id
     */
    private List<Long> cartIdList;

    /**
     * 收货地址id
     */
    private Long addressId;

    /**
     * 备注
     */
    private String remark;
}
