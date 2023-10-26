package com.hixtrip.sample.infra.db.dataobject;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDO {
    /**
     * 订单id
     */
    private String id;

    /**
     * 购买人
     */
    private String userId;

    /**
     * 卖家id
     */
    private String sellerId;

    /**
     * sku_id
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
     *
     */
    private LocalDateTime payTime;

    /**
     * 支付状态: 0 未支持 1 已支付
     */
    private String payStatus;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private Integer delFlag;

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

}
