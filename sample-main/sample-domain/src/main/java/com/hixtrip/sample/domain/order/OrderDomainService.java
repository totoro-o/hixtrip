package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.dto.ApiResult;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 * 这是领域服务的示例，领域服务只处理域内业务, 返回的领域对象由DO转换
 */
@Component
public class OrderDomainService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Autowired
    private CommodityDomainService commodityDomainService;

    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public ApiResult<?> createOrder(Order order) {
        //验证订单信息
        //商品是否存在
        String skuId = order.getSkuId();
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(skuId);
        if (skuPrice == null) {
            return ApiResult.failed(10001);
        }
        //库存是否足够
        Integer inventory = inventoryDomainService.getInventory(skuId);
        if (inventory == null || inventory <= 0) {
            return ApiResult.failed(20001);
        }
        //订单金额是否正确
        if (!checkMoney(order)){
            return ApiResult.failed(30001);
        }

        ApiResult<?> resultOrder = orderRepository.createOrder(order);
        if (resultOrder.isSuccess()) {
            //待付款订单创建成功,通知前端发起支付流程，相关信息包装后返回
            return ApiResult.success(resultOrder.getData());
        }
        //待付款订单创建失败
        return ApiResult.failed(40001);
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参

    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参

    }

    /**
     * 后期可以使用注解实现
     * 订单日志
     */
    public void orderLog(Order order ,Object... args) {

    }

    /**
     * 校验订单价格是否正确的专用方法
     * 优惠卷之类逻辑也在此
     */
    public boolean checkMoney(Order order){
        return Boolean.TRUE;
    }
}
