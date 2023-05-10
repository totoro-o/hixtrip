package com.hixtrip.sample.entry;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.OrderReq;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.util.RespCode;
import com.hixtrip.sample.util.RespResult;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * todo 这是你要实现的
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderDomainService;

    /**
     * todo 这是你要实现的接口
     * @param s 请修改入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/create")
    public RespResult order(@RequestBody OrderReq orderReq) {
        //登录信息可以在这里模拟
        var userName = "xiaoming";

        orderReq.setUsername(userName);

        return orderDomainService.createOrder(orderReq);

    }

    /**
     * todo 这是模拟创建订单后，支付结果的回调通知
     * @param paramsMap 请修改入参对象 回调参数
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    public String payCallback(@RequestBody Map<String , String > paramsMap) {

        // 参数解密操作

        //判断支付结果状态  日志状态：2 成功 ， 7 失败
        int status = 7;
        // return_code/result_code
        if(!paramsMap.get("return_code").equals("00") && !paramsMap.get("result_code").equals("00")){
            return "fail";
        }

        // 更新订单状态 可使用队列方式异步处理，这里直接调用
        orderDomainService.updateAfterPayStatus(paramsMap.get("orderId"));

        // 默认以上业务正常，给三方回复OK等信息

        return "ok";
    }

}
