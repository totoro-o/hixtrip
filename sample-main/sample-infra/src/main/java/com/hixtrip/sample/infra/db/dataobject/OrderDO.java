package com.hixtrip.sample.infra.db.dataobject;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @CreateDate: 2023/10/24
 * @Author: ccj
 */
@Data
public class OrderDO {

    /**
     * 订单号
     */
    private String id;


    /**
     * 购买人
     */
    private String userId;

    /**
     * 卖家 id
     */
    private String sellerId;

    /**
     * SkuId
     */
    private String skuId;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * 购买金额
     */
    private BigDecimal money;

    /**
     * 购买时间
     */
    private LocalDateTime payTime;

    /**
     * 支付状态
     */
    private String payStatus;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private Long delFlag;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;




    /**
     * 分库键
     */
    private String dbShardingKey;


    /**
     * 分表键
     */
    private String tbShardingKey;


}
