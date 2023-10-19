package com.hixtrip.sample.client.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * <p> 订单支付信息VO
 *
 * @author airness
 * @since 2023/10/18 19:49
 */
@Data
public class OrderPaymentVO {

    private String orderId;

    private Integer skuId;

    private BigDecimal money;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderPaymentVO that = (OrderPaymentVO) o;

        return Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return orderId != null ? orderId.hashCode() : 0;
    }
}
