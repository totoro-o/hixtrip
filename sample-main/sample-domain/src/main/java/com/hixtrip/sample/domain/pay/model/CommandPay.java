package com.hixtrip.sample.domain.pay.model;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandPay {

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 订单状态
     */
    private String payStatus;



    @Getter
    @AllArgsConstructor
    public enum PayStatus {
        // 订单处理成功
        PAID("paid"),
        // 以下均为失败状态
        TIMEOUT("timeout"),
        CANCEL("cancel")
        ;
        private final String status;

        public boolean valid(String status) {
            return this.status.equals(status);
        }
    }


    /**
     * 检查状态是否匹配
     * @param status 要检查的状态
     * @return true表示状态匹配
     */
    public boolean isStatusValid(PayStatus status) {
        return status.valid(this.payStatus);
    }
}