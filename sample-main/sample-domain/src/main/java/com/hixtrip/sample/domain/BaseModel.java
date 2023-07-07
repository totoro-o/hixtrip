package com.hixtrip.sample.domain;

import java.io.Serializable;

public class BaseModel<T> implements Serializable {

    public T getBean(String beanName){
        return (T) BeanUtils.getApplicationContext().getBean(beanName);
    }
}
