package com.hixtrip.sample.domain.pay.constants;

import com.hixtrip.sample.domain.pay.callback.FailPayCallback;
import com.hixtrip.sample.domain.pay.callback.RepeatPayCallback;
import com.hixtrip.sample.domain.pay.callback.SuccessPayCallback;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
public enum PayStatusEnum {

    Success("支付成功", "success", SuccessPayCallback.class.getAnnotation(Component.class).value()),
    Fail("支付失败", "fail", FailPayCallback.class.getAnnotation(Component.class).value()),
    Repeat("重复支付", "repeat", RepeatPayCallback.class.getAnnotation(Component.class).value());


    PayStatusEnum(String name, String status, String beanName) {
        this.name = name;
        this.status = status;
        this.beanName = beanName;
    }

    public static PayStatusEnum getByStatus(String status) {

        if (status == null) {
            throw new RuntimeException("找不到枚举值");
        }
        for (PayStatusEnum v : values()) {
            if (v.status.equals(status)) {
                return v;
            }
        }
        throw new RuntimeException("找不到枚举值");
    }

    private String name;

    private String status;

    private String beanName;
}
