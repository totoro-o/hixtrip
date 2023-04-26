package com.hixtrip.sample.entry;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * todo 这是你要实现的
 */
@RestController
public class OrderController {

    /**
     * todo 这是你要实现的接口
     * @param s 请修改入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/create")
    public String order(@RequestBody String s) {
        //登录信息可以在这里模拟
        var userId = "";
        return "";
    }

    /**
     * todo 这是模拟创建订单后，支付结果的回调通知
     * @param s 请修改入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    public String payCallback(@RequestBody String s) {
        return "";
    }

}
