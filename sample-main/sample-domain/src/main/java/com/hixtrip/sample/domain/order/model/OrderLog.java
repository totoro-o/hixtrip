package com.hixtrip.sample.domain.order.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderLog {
    private Long id;

    private Long orderId;

    private String content;

    private LocalDateTime createTime;
}
