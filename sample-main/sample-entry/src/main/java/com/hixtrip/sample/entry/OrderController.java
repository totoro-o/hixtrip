package com.hixtrip.sample.entry;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * todo 这是你要实现的
 */
@RestController
public class OrderController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderService orderService;

    /**
     * todo 这是你要实现的接口
     *
     * @param commandOderCreateDTO 入参对象
     * @param request 请求信息
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/create")
    public String order(@RequestBody CommandOderCreateDTO commandOderCreateDTO,HttpServletRequest request) {
        //登录信息可以在这里模拟
        //假如 当用户登录时，将用户信息存入redis缓存中<token,user>，可以用以下方式获取。
        if(request.getAttribute("Authorization") == null){
            return "获取登录信息失败";
        }
        //获取token
        String token = request.getAttribute("Authorization").toString();
        if(token.isEmpty()){
            return "获取登录信息失败";
        }
        //通过token获取redis缓存的用户信息
        Object userObj = redisTemplate.opsForValue().get(token);
        if(userObj==null){
            return "获取登录信息失败";
        }
        //User user = (User)userObj;
        //var userId = user.getId();
        String loginUserId = "123456";
        commandOderCreateDTO.setUserId(loginUserId);
        try {
            Order order = orderService.createOrder(commandOderCreateDTO);
        }catch (RuntimeException re){
            return "创建订单失败,异常【"+re.getMessage()+"】";
        }catch (Exception e){
            return "创建订单失败,未知异常，请联系管理员";
        }
        return "创建订单成功";
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
        try {
            if(commandPayDTO.getOrderId()==null || commandPayDTO.getPayStatus()==null
                || commandPayDTO.getOrderId().isEmpty() || commandPayDTO.getPayStatus().isEmpty()){
                return "订单id和订单状态不能为空";
            }
            orderService.payCallback(commandPayDTO);
        }catch (RuntimeException re){
            return "异常【"+re.getMessage()+"】";
        }catch (Exception e){
            return "请求失败,未知异常，请联系管理员";
        }
        return "请求成功";
    }

}
