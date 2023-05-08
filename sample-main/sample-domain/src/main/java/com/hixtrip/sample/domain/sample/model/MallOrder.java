package com.hixtrip.sample.domain.sample.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MallOrder {
    private Long id;
    private Long orderId;
    private Long skuId;
    private float price;
    private Integer status;
    private Date createTime;
    private Long userId;
    private String remark;
    private Integer quantity;

    private boolean success;
    private String errorMsg;
}
