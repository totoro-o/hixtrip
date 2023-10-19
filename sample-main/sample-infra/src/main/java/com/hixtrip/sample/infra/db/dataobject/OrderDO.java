package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p> 订单买家表（主表）
 *
 * @author airness
 * @since 2023/10/19 12:25
 */
@Data
@EqualsAndHashCode
@TableName("order_primary")
public class OrderDO {

    @TableId(type = IdType.ASSIGN_UUID)
    private String orderId;
    private String userId;
    private String sellerId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createBy;
    private String updateBy;
    private String orderStatus;
    private String payStatus;
    private BigDecimal money;
    private String outTradePayNo;
    private String skuId;
    private Integer amount;
    private String description;
    private Boolean delFlag;
}
