package com.hixtrip.sample.domain.pay.event;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 13:23
 * 事件基类
 */
@Getter
public abstract class BaseEvent extends ApplicationEvent {

    private static final EventPublisher eventPublisher;

    static {
        eventPublisher = ApplicationContextUtils.getBean(EventPublisher.class);
    }
    public BaseEvent(Object source) {
        super(source);
    }

    /**
     * 领域事件推送
     */
    public void publish() {
        eventPublisher.publishEvent(this);
    }
}
