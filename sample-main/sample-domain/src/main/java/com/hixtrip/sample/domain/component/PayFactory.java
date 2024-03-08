package com.hixtrip.sample.domain.component;

import com.hixtrip.sample.common.enums.ProductOrderPayTypeEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 策略 + 简单工厂模式
 */
@Service
public class PayFactory {

    @Autowired
    private PaySuccessStrategy paySuccessStrategy;

    @Autowired
    private PayFailStrategy payFailStrategy;

    @Autowired
    private RepeatPayStrategy reRepeatPayStrategy;


    /**
     * 支付成功回调
     * @param commandPay
     * @return
     */
    public String payCallbackSuccess(CommandPay commandPay) {
        String payType = commandPay.getPayType();
        if (ProductOrderPayTypeEnum.ALIPAY.name().equalsIgnoreCase(payType)) {
            //支付宝支付 对应策略（未实现）
        } else if (ProductOrderPayTypeEnum.WECHAT.name().equalsIgnoreCase(payType)) {
            //微信支付 对应策略（未实现）
        }
        PayCallbackContext payStrategyContext = new PayCallbackContext(paySuccessStrategy);
        payStrategyContext.payCallback(commandPay);
        return "success";
    }

    /**
     * 支付失败回调
     * @param commandPay
     * @return
     */
    public String payCallbackFail(CommandPay commandPay) {
        String payType = commandPay.getPayType();
        if (ProductOrderPayTypeEnum.ALIPAY.name().equalsIgnoreCase(payType)) {
            //支付宝支付
        } else if (ProductOrderPayTypeEnum.WECHAT.name().equalsIgnoreCase(payType)) {
            //微信支付
        }

        PayCallbackContext payStrategyContext = new PayCallbackContext(payFailStrategy);
        payStrategyContext.payCallback(commandPay);
        return "fail";
    }

    /**
     * 重复支付
     * @param commandPay
     * @return
     */
    public String repayCallback(CommandPay commandPay) {
        PayCallbackContext payStrategyContext = new PayCallbackContext(reRepeatPayStrategy);
        payStrategyContext.payCallback(commandPay);
        return "repeat";
    }

    public String getRsStr(CommandPay commandPay) {
        String rs="";
        if (commandPay.getPayStatus().equals("success")) {
            rs=payCallbackSuccess(commandPay);
        }else if (commandPay.getPayStatus().equals("finish")) {
            rs=repayCallback(commandPay);
        }else{
            rs=payCallbackFail(commandPay);
        }
        return rs;
    }


}
