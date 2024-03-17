package com.hixtrip.sample.client.order.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 订单 创建返回实体
 * 前端/产品 如无特殊需求，返回最少字段即可
 */
@Data
@Builder
public class OrderCreatedVO {

    /**
     * 订单号
     */
    private String id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
