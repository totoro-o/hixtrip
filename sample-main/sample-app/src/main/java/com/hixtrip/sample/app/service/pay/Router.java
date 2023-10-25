package com.hixtrip.sample.app.service.pay;

import com.hixtrip.sample.domain.pay.enmus.PayStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @CreateDate: 2023/10/25
 * @Author: ccj
 */
@Data
@AllArgsConstructor
public class Router {

    private PayStatusEnum payStatus;
    // more 更细粒度

}
