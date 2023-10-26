package com.hixtrip.sample.entry;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.order.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * todo 这是你要实现的
 */
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    /**
     * todo 这是你要实现的接口
     *
     * @param commandOderCreateDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/create")
    public ResultVO<String> order(@RequestBody CommandOderCreateDTO commandOderCreateDTO) {
        // 参数校验，一般用注解校验
        Assert.hasLength(commandOderCreateDTO.getSkuId(), "skuId不能为空");
        Assert.notNull(commandOderCreateDTO.getAmount(), "amount不能为空");
        Assert.isTrue(commandOderCreateDTO.getAmount() > 0, "amount必须大于0");

        // TODO 模拟用户信息
        var userId = "uid-1";
        commandOderCreateDTO.setUserId(userId);

        String orderId = orderService.createOrder(commandOderCreateDTO);
        return ResultVO.ok(orderId);
    }

    /**
     * todo 这是模拟创建订单后，支付结果的回调通知
     * 【中、高级要求】需要使用策略模式处理至少三种场景：支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
     *
     * @param commandPayDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    public ResultVO<Void> payCallback(@RequestBody CommandPayDTO commandPayDTO) {
        String orderId = commandPayDTO.getOrderId();
        Assert.hasLength(orderId, "orderId不能为空");
        String payStatus = commandPayDTO.getPayStatus();
        Assert.hasLength(payStatus, "payStatus不能为空");

        orderService.payCallback(commandPayDTO);
        return ResultVO.ok();
    }

}
