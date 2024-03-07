package com.hixtrip.sample.app.component;

import com.hixtrip.sample.client.order.vo.PayCallbackVO;
import com.hixtrip.sample.common.enums.ProductOrderPayTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayFactory {

    @Autowired
    private AlipayCallbackStrategy alipayCallbackStrategy;

    @Autowired
    private WechatPayCallbackStrategy wechatPayCallbackStrategy;

    /**
     * 支付成功回调
     * @param payCallbackVO
     * @return
     */
    public String payCallbackSuccess(PayCallbackVO payCallbackVO) {
        String payType = payCallbackVO.getPayType();
        if (ProductOrderPayTypeEnum.ALIPAY.name().equalsIgnoreCase(payType)) {
            //支付宝支付
            PayCallbackContext payStrategyContext = new PayCallbackContext(alipayCallbackStrategy);
            return payStrategyContext.executePaySuccess();
        } else if (ProductOrderPayTypeEnum.WECHAT.name().equalsIgnoreCase(payType)) {
            //微信支付 暂未实现
            PayCallbackContext payStrategyContext = new PayCallbackContext(wechatPayCallbackStrategy);
            return payStrategyContext.executePaySuccess();
        }
        return null;
    }

    /**
     * 支付失败回调
     * @param payCallbackVO
     * @return
     */
    public String payCallbackFail(PayCallbackVO payCallbackVO) {
        String payType = payCallbackVO.getPayType();
        if (ProductOrderPayTypeEnum.ALIPAY.name().equalsIgnoreCase(payType)) {
            //支付宝支付
            PayCallbackContext payStrategyContext = new PayCallbackContext(alipayCallbackStrategy);
            return payStrategyContext.executePayFail();
        } else if (ProductOrderPayTypeEnum.WECHAT.name().equalsIgnoreCase(payType)) {
            //微信支付 暂未实现
            PayCallbackContext payStrategyContext = new PayCallbackContext(wechatPayCallbackStrategy);
            return payStrategyContext.executePayFail();
        }
        return null;
    }


}
