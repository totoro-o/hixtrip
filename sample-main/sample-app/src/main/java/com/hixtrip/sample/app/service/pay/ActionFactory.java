package com.hixtrip.sample.app.service.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @CreateDate: 2023/10/25
 * @Author: ccj
 */
@Service
public class ActionFactory {
    private static final Map<Router, PayAction> ACTION_MAP = new HashMap<>();

    @Autowired
    private final List<PayAction> actions = new ArrayList<>();

    @PostConstruct
    public void buildActionMap() {
        Assert.notEmpty(actions, "回调执行器集合不能为空");

        actions.forEach(item -> {
            Router router = item.getRouter();
            Assert.notNull(router, "回调操作模式为空");
            ACTION_MAP.put(router, item);
        });
    }

    public static PayAction getAction(Router router) {
        PayAction action = ACTION_MAP.get(router);
        Assert.notNull(action, "不支持的回调状态操作" + router.getPayStatus().getCode());
        return action;
    }

}
