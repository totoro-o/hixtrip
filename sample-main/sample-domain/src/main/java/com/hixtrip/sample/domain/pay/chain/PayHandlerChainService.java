package com.hixtrip.sample.domain.pay.chain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PayHandlerChainService {

    @Autowired
    private ApplicationContext applicationContext;

    //将继承PayHandler的bean构造成链表
    public PayHandler getHandlerChain() {
        Map<String, PayHandler> handlerBeans = applicationContext.getBeansOfType(PayHandler.class);

        PayHandler head = null;
        PayHandler prev = null;

        for (PayHandler handler : handlerBeans.values()) {
            if (prev != null) {
                prev.setNextHandler(handler);
            }
            if (head == null) {
                head = handler;
            }
            prev = handler;
        }

        return head;
    }
}

