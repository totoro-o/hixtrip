package com.hixtrip.sample.entry;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.order.OrderCreateReq;
import com.hixtrip.sample.client.order.OrderCreateVO;
import com.hixtrip.sample.domain.em.EnumPayPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * todo 这是你要实现的
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 您好这是接口响应说明：
     *
     * 一般返回给前端的数据格式均有统一格式
     * 建议可以在响应时统一封装 接口只返回具体的业务逻辑数据 即：data 这样可以更关注业务本身
     *
     * 具体实现方法可以为 继承 ResponseBodyResultHandler 对象 重写 handleReturnValue 方法 实现
     * 如部分接口 无须返回统一格式，也可自定义注解如 @NotPkg 用于接口标识 在封装接口时判断不进行封装
     *
     * 异常也由外部统一处理，拦截异常 如业务异常（ BusinessException ）、系统异常（NullPointerException）等
     *
     * 具体实现方法可以为使用注解 @ControllerAdvice
     *     @ResponseBody
     *     @ExceptionHandler(value = Throwable.class)
     *
     * 正常业务返回：
     * {
     *     code:200,
     *     msg:"success",
     *     data:{}
     * }
     *
     * 业务返回：
     * {
     *   code:业务码
     *   msg: 抛的业务异常消息
     * }
     *
     * 系统异常返回： 或可根据项目规范 修改对应的 http status
     *
     * {
     *     code:500, 或 httpcode
     *     msg:"系统异常"
     * }
     *
     *
     */

    /**
     * todo 这是你要实现的接口
     *
     * @param s 请修改入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/create")
    public OrderCreateVO order(@RequestBody OrderCreateReq req) {
        //登录信息可以在这里模拟
        Long userId = 0L;
        return orderService.create(userId, req);
    }


    /**
     * todo 这是模拟创建订单后，支付结果的回调通知
     * 需要使用策略模式处理至少三种场景：支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
     *
     * @param s           请修改入参对象
     * @param payPlatform 回调地址拼接平台类型 用于辨别来源
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    public Object payCallback(@RequestParam Integer payPlatform, @RequestBody Object paramsReq) {
        EnumPayPlatform enumPayPlatform = EnumPayPlatform.getEnum(payPlatform);
        return orderService.payCallback(enumPayPlatform, paramsReq);
    }

}
