package com.hixtrip.sample.app.service.pay;

import com.hixtrip.sample.domain.pay.command.PayCommand;

/**
 * @CreateDate: 2023/10/25
 * @Author: ccj
 */
public interface PayAction {
    /**
     * 回调操作
     */
    boolean execute(PayCommand context);

    /**
     * 获取路由
     */
    Router getRouter();

}
