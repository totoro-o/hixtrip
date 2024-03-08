package com.hixtrip.sample.infra.db.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author lmk
 * @create 2024/3/7 16:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class SkuDo {

    /**
     * Id
     */
    private String id;

    private Long sellableQuantity;

    private Long withholdingQuantity;

    private Long occupiedQuantity;
    /**
     * SkuId
     */
    private Integer count;

    /**
     * 购买金额
     */
    private BigDecimal money;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}

