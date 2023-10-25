package com.hixtrip.sample.domain.order.command;

import lombok.Data;

/**
 * @CreateDate: 2023/10/25
 * @Author: ccj
 */
@Data
public class CreateOrderCommand {

    /**
     * 商品规格id
     */
    private String skuId;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * 用户id
     */
    private String userId;

}
