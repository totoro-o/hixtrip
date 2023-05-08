package com.hixtrip.sample.domain.sample.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@Builder
public class MallStock {
    private Long id;
    private Long skuId;
    private Long quantity;
    private Date createTime;
}
