package com.hixtrip.sample.domain.pay.command;

import com.hixtrip.sample.domain.pay.enmus.PayStatusEnum;
import lombok.Data;

/**
 * @CreateDate: 2023/10/25
 * @Author: ccj
 */
@Data
public class PayCommand {
    /**
     * 订单id
     */
    private String orderId;

    /**
     * 支付状态
     */
    private PayStatusEnum payStatus;
}
