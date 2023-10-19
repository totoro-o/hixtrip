package com.hixtrip.sample.domain.order.model;

import com.hixtrip.sample.domain.common.AuthenticationThreadHolder;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

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
     * 订单状态
     */
    private String orderStatus;

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
     * 初始化订单
     */
    public void initOrder(@NotNull Long serialId) {
        this.createBy = userId;
        // 订单ID初始化
        id = getOrderId(serialId);
        orderStatus = OrderStatus.open.name();
        payStatus = PayStatus.un_pay.name();
        delFlag = 0L;
    }

    /**
     * 更新订单信息
     */
    public void initOrderMoney(BigDecimal unitMoney) {
        this.money = unitMoney.multiply(BigDecimal.valueOf(amount));
    }


    /**
     * 关闭订单
     * @throws RuntimeException 尝试关闭finished订单时抛出
     */
    public void closeOrder() throws RuntimeException{
        if (OrderStatus.finished.valid(orderStatus)) {
            throw new RuntimeException("订单已经完成，无法关闭");
        }
        orderStatus = OrderStatus.closed.name();
        updateBy = AuthenticationThreadHolder.getUserId();
        updateTime = LocalDateTime.now();
    }

    public void orderPay() {
        if (!PayStatus.un_pay.valid(payStatus)) {
            throw new RuntimeException("重复支付订单");
        }
        LocalDateTime now = LocalDateTime.now();
        payTime = now;
        updateTime = now;
        updateBy = AuthenticationThreadHolder.getUserId();
        payStatus = PayStatus.paid.name();
    }



    /**
     * Id生成规则使用1位版本号、日期小时时间戳编码10位、小时序列号5位、用户ID后缀4位
     * 此ID可容纳每日百万订单新增，并且不会产生重复订单
     * @param serialId 序列ID
     * @return 返回
     */
    private String getOrderId(@NotNull Long serialId) {
        // 详细流程省略，生成ID样例如下 ”12023101819000010001“
        // 假设用户ID末尾采用数字形式进行编码，且数字呈现递增关系，这里生成订单的Id
        return null;
    }



    public enum PayStatus {
        un_pay, paid;

        public boolean valid(String status) {
            return name().equals(status);
        }
    }

    public enum OrderStatus {
        open, finished, closed;

        public boolean valid(String status) {
            return name().equals(status);
        }
    }


}
