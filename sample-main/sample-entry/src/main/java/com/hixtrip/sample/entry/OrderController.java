package com.hixtrip.sample.entry;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.PayCallbackRequest;
import com.hixtrip.sample.domain.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * todo 这是你要实现的
 */
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * todo 这是你要实现的接口
     * @param request 请修改入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/create")
    public String order(@RequestBody Order request) {
        //传入的应该是String 就不做反序列化操作了
        //登录信息可以在这里模拟
        String userId = "";
        Order order = new Order();
        // 其他订单相关参数的设置
        order.setUserId(userId);
        order.setSkuId(request.getSkuId());
        //...
       orderService.createOrder(request);
        return "SUCCESS";

    }

    /**
     * todo 这是模拟创建订单后，支付结果的回调通知
     * 需要使用策略模式处理至少三种场景：支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
     * @param request 请修改入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    public String payCallback(@RequestBody PayCallbackRequest request) {
       return orderService.handPayCallback(request);

    }

}
