package com.hixtrip.sample.domain.order.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;

    private String orderNo;

    private Long userId;

    private String skuId;

    private Long num;

    private BigDecimal skuPrice;

    private BigDecimal totalAmount;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String payStatus;

    private LocalDateTime payTime;

    private String payUrl;

    private BigDecimal payAmount;

    /**
     * 第三方流水号 如微信支付的transaction_id
     */
    private String thirdPartySerialNumber;

    /**
     * @return true表示这笔订单的回调已经处理过
     */
    public boolean checkOrderHandleComplete() {
        return !Order.PayStatusEnum.WAIT_PAY.getCode().equals(getPayStatus());
    }

    public void calcTotalAmount() {
        this.totalAmount = this.skuPrice.multiply(BigDecimal.valueOf(this.num));
    }

    public boolean duplicatePay(String paySerialNumber) {
        if (isEmpty(paySerialNumber) || isEmpty(this.thirdPartySerialNumber)) {
            return false;
        }
        return !paySerialNumber.equals(this.thirdPartySerialNumber);
    }

    private static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    @AllArgsConstructor
    @Getter
    public enum PayStatusEnum {
        WAIT_PAY("WAIT_PAY", "待支付"),
        PAY_SUCCESS("PAY_SUCCESS", "支付成功"),
        PAY_FAIL("PAY_FAIL", "支付失败");

        private final String code;
        private final String value;
    }
}
