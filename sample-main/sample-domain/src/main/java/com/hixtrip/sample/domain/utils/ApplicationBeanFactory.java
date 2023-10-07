package com.hixtrip.sample.domain.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationBeanFactory implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static <T> T getBean(String className, Class<T> clzss) throws BeansException, IllegalArgumentException {
        if (className == null || className.length() <= 0) {
            throw new IllegalArgumentException("className为空");
        }

        String beanName = null;
        if (className.length() > 1) {
            beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
        } else {
            beanName = className.toLowerCase();
        }
        return applicationContext != null ? (T) applicationContext.getBean(beanName) : null;
    }

}
