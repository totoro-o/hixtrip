package com.hixtrip.sample.entry;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.util.ValidateUtil;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.sample.vo.ResultVo;
import com.hixtrip.sample.domain.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
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
     *
     * @param commandOderCreateDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/create")
    public ResultVo order(@RequestBody CommandOderCreateDTO commandOderCreateDTO) throws Exception {
        ResultVo resultVo = new ResultVo();
        // 校验必填
        ValidateUtil.validate(commandOderCreateDTO);
        // 创建订单
        orderService.createOrder(commandOderCreateDTO);
        resultVo.setSuccess(true);
        resultVo.setMsg("创建订单成功！");
        return resultVo;

    }

    /**
     * todo 这是模拟创建订单后，支付结果的回调通知
     * 【中、高级要求】需要使用策略模式处理至少三种场景：支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
     *
     * @param commandPayDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    public ResultVo payCallback(@RequestBody CommandPayDTO commandPayDTO) throws Exception {
        ResultVo resultVo = new ResultVo();
        // 校验必填
        ValidateUtil.validate(commandPayDTO);
        // 根据支付状态处理订单
        orderService.payCallback(commandPayDTO);
        resultVo.setSuccess(true);
        resultVo.setMsg("支付回调成功！");
        return resultVo;
    }


}
