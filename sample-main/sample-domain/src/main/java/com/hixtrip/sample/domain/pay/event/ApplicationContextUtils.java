package com.hixtrip.sample.domain.pay.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 13:23
 * spring工具类
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.applicationContext = applicationContext;
    }

    /**
     * 获取Spring上下文
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取Bean
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {


        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     * 52      *
     * 53      * @param name
     * 54      * @param clazz
     * 55      * @param <T>
     * 56      * @return
     * 57
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}