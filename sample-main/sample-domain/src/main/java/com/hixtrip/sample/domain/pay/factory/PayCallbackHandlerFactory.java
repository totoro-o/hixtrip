package com.hixtrip.sample.domain.pay.factory;

import com.hixtrip.sample.domain.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.handler.PayCallbackHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付回调工厂类
 */
@Component
public class PayCallbackHandlerFactory implements InitializingBean {

    @Autowired
    private List<PayCallbackHandler> payCallbackHandlerList;

    private final Map<PayStatusEnum, PayCallbackHandler> handlerMap = new HashMap<>();

    /**
     * 获取具体处理器
     *
     * @param payStatus 支付状态
     * @return 返回具体支付回调的处理器
     */
    public PayCallbackHandler getHandler(String payStatus) {
        return handlerMap.get(PayStatusEnum.getEnumFromStatus(payStatus));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        payCallbackHandlerList.forEach(payCallbackHandler -> {
            handlerMap.put(payCallbackHandler.getPayStatus(), payCallbackHandler);
        });
    }
}
