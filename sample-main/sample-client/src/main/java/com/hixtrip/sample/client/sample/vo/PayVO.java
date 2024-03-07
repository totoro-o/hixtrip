package com.hixtrip.sample.client.sample.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 类说明
 *
 * @author gongjs
 * @date 2024/3/7
 */
@Data
@Builder
public class PayVO {

    /**
     * 订单id
     */
    private String id;

    /**
     * 状态码
     */
    private String code;

    /**
     * 信息
     */
    private String msg;
}
