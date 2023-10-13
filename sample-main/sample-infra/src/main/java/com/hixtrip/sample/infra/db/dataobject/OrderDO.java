package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 *
 * @TableName order
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "t_trade_order", autoResultMap = true)
@SuperBuilder(toBuilder = true)
public class OrderDO {
    /**
     * 订单号
     */
    @TableId(value = "id")
    private String id;

    /**
     * 购买人
     */
    @TableField(value = "user_id")
    private String userId;

    @TableField(value = "seller_id")
    private String sellerId;

    /**
     * skuId
     */
    @TableField(value = "sku_id")
    private String skuId;

    /**
     * 购买数量
     */
    @TableField(value = "amount")
    private Integer amount;

    /**
     * 购买金额
     */
    @TableField(value = "money")
    private BigDecimal money;

    /**
     * 购买时间
     */
    @TableField(value = "pay_time")
    private LocalDateTime payTime;

    /**
     * 支付状态
     */
    @TableField(value = "pay_status")
    private String payStatus;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableField(value = "del_flag")
    private Long delFlag;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 修改时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
