package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "order", autoResultMap = true)
@SuperBuilder(toBuilder = true)
public class OrderDO extends Model<OrderDO> {


    /**
     * 订单号
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 购买人
     */
    @TableId(value = "user_id")
    private String userId;


    /**
     * SkuId
     */
    @TableId(value = "sku_id")
    private String skuId;

    /**
     * 购买数量
     */
    @TableId(value = "amount")
    private Integer amount;

    /**
     * 购买金额
     */
    @TableId(value = "money")
    private BigDecimal money;

    /**
     * 购买时间
     */
    @TableId(value = "pay_time")
    private LocalDateTime payTime;

    /**
     * 支付状态
     */
    @TableId(value = "pay_status")
    private String payStatus;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableId(value = "del_flag")
    private Long delFlag;

    /**
     * 创建人
     */
    @TableId(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableId(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @TableId(value = "update_by")
    private String updateBy;

    /**
     * 修改时间
     */
    @TableId(value = "update_time")
    private LocalDateTime updateTime;

}
