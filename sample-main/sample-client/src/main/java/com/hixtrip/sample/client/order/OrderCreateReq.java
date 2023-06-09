package com.hixtrip.sample.client.order;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class OrderCreateReq {

    /**
     * 购物车id
     */
    private List<Long> cartId;

    /**
     * 收货地址id
     */
    private Long addressId;

    /**
     * 备注
     */
    private String remark;

}
