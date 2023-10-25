package com.hixtrip.sample.app.service.pay.action;

import com.hixtrip.sample.app.service.pay.PayAction;
import com.hixtrip.sample.app.service.pay.Router;
import com.hixtrip.sample.domain.pay.command.PayCommand;
import com.hixtrip.sample.domain.pay.enmus.PayStatusEnum;
import org.springframework.stereotype.Service;

/**
 * @CreateDate: 2023/10/25
 * @Author: ccj
 */
@Service
public class FailPayAction implements PayAction {

    @Override
    public boolean execute(PayCommand context) {
        // more
        return false;
    }

    @Override
    public Router getRouter() {
        return new Router(PayStatusEnum.FAIL);
    }
}
