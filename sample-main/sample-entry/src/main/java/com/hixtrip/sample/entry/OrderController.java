package com.hixtrip.sample.entry;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.common.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * todo 这是你要实现的
 */
@Slf4j
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * todo 这是你要实现的接口
     *
     * @param commandOderCreateDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/create")
    public void order(@RequestBody CommandOderCreateDTO commandOderCreateDTO,HttpServletResponse response) {
        //登录信息可以在这里模拟
        /**
         * 分布式场景下一般用 threadLocal 去获得用户信息
         * LoginUser loginUser = LoginInterceptor.threadLocal.get();
         */
        var userId = "";
        commandOderCreateDTO.setUserId(userId);
        JsonData jsonData = orderService.order(commandOderCreateDTO);
        if (jsonData.getCode() == 0) {
            //跳转第三方支付页面，如alipay、wxPay等
            writeData(response,jsonData);
        }else{
            log.error("创建订单失败{}",jsonData.toString());
        }
    }

    /**
     * todo 这是模拟创建订单后，支付结果的回调通知
     * 【中、高级要求】需要使用策略模式处理至少三种场景：支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
     *
     * @param commandPayDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    public String payCallback(@RequestBody CommandPayDTO commandPayDTO) {
        JsonData rs = orderService.payCallback(commandPayDTO);
        return rs.getData().toString();
    }


    /**
     * 写出页面信息
     * @param response
     * @param jsonData
     */
    private void writeData(HttpServletResponse response,JsonData jsonData){
        try{
            response.setContentType("text/html;charset=UTF8");
            response.getWriter().write(jsonData.getData().toString());
            response.getWriter().flush();
            response.getWriter().close();
        }catch (IOException e){
            log.error("写出Html异常:{}",e) ;
        }
    }
}
