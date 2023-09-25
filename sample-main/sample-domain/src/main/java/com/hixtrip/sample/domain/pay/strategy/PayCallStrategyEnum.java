package com.hixtrip.sample.domain.pay.strategy;

public enum PayCallStrategyEnum {
    DUPLICATE(PayStatus.DUPLICATE, "duplicatePaymentStrategy"),
    FAIL(PayStatus.FAIL, "paymentFailureStrategy"),
    SUCCESS(PayStatus.SUCCESS, "paymentSuccessStrategy");

    private String label;

    private String beanName;

    PayCallStrategyEnum(String label, String beanName) {
        this.label = label;
        this.beanName = beanName;
    }

    public static PayCallStrategyEnum getByLabel(String label) {
        PayCallStrategyEnum[] values = PayCallStrategyEnum.values();
        for (PayCallStrategyEnum strategyEnum : values) {
            if (strategyEnum.getLabel().equals(label)) {
                return strategyEnum;
            }
        }
        return null;
    }

    public String getBeanName() {
        return beanName;
    }

    public String getLabel() {
        return label;
    }
}
