package com.hixtrip.sample.entry;

import com.hixtrip.sample.client.BaseVO;
import com.hixtrip.sample.client.CreateOrderParam;
import com.hixtrip.sample.client.PayBackParam;
import com.hixtrip.sample.client.ResultCode;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.sample.model.MallOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * todo 这是你要实现的 OK
 */
@RestController
public class OrderController {


    private OrderDomainService orderDomainService;

    @Autowired
    public void setOrderDomainService(OrderDomainService orderDomainService) {
        this.orderDomainService = orderDomainService;
    }

    /**
     * todo 这是你要实现的接口 OK
     *
     * @param param 请修改入参对象
     * @return BaseVO 请修改出参对象
     */
    @PostMapping(path = "/command/order/create")
    public BaseVO<?> order(@RequestBody CreateOrderParam param) {
        //登录信息可以在这里模拟
        var userId = 1;
        if (param.getQuantity() <= 0) {
            return BaseVO.fail(ResultCode.ILLEGAL_ARGUMENT);
        }
        MallOrder mallOrder = orderDomainService.create(userId, param.getSkuId(), param.getQuantity());
        if (mallOrder == null || !mallOrder.isSuccess()) {
            return BaseVO.fail(ResultCode.FAILED_ORDER);
        }
        return BaseVO.success(mallOrder.getOrderId());
    }

    /**
     * todo 这是模拟创建订单后，支付结果的回调通知 OK
     * 需要使用策略模式处理至少三种场景：支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
     *
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    public String payCallback(@RequestBody PayBackParam payBackParam) {
        orderDomainService.orderPaySuccess(payBackParam.getOrderId());
        //根据支付方,返回对应的协定值
        return "ok";
    }

}
