package com.hixtrip.sample.domain.order.model;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.dto.OrderDomainCreateCmd;
import com.hixtrip.sample.domain.pay.enums.PayStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Order {

    /**
     * 订单号
     */
    private String id;


    /**
     * 购买人
     */
    private String userId;


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
     * 创建订单
     * @param cmd
     * @return
     */
    public static Order createOrder(OrderDomainCreateCmd cmd) {
        return Order.builder()
                .userId(cmd.getUserId())
                .skuId(cmd.getSkuId())
                .amount(cmd.getAmount())
                .money(cmd.getMoney())
                .payStatus(PayStatus.UNPAID.name())
                .createBy("System")
                .updateBy("System")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
    }

    /**
     * 更新订单状态
     */
    public void updatePaid() {
        this.payStatus = PayStatus.PAID.name();
        this.payTime = LocalDateTime.now();
    }

    /**
     * 更新订单状态
     */
    public void updatePayFailed() {
        this.payStatus = PayStatus.PAY_FAILED.name();
    }
}
