package com.hixtrip.sample.domain.sample.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MallSku {
    private Long id;

    private String name;

    private String description;

    private Float price;

    private Date createTime;
}
