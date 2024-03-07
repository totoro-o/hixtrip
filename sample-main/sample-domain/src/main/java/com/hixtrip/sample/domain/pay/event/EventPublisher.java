package com.hixtrip.sample.domain.pay.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 14:50
 * Spring 事件发送器
 */
@Component
public class EventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(ApplicationEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
