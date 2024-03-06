package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @author HuYuDe
 */
@Component
public class OrderRepositoryImpl implements OrderRepository {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Override
    public void createOrder(Order order) {
        //订单生成前 修改库存
        Boolean aBoolean = inventoryDomainService.changeInventory(order.getSkuId(), null, order.getAmount(), null);
        if(!aBoolean){
            throw new RuntimeException("该商品库存不足");
        }

        //正常创建
        OrderDO orderDO = OrderDOConvertor.INSTANCE.doToDomain(order);
        //支付状态0为待付款
        orderDO.setPayStatus("0");

        //入库
        orderMapper.insert(orderDO);
    }

    /**
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay){
        OrderDO order=orderMapper.selectById(commandPay.getOrderId());
        if(Objects.isNull(order)){
            throw new RuntimeException("当前订单不存在");
        }
        order.setPayStatus(commandPay.getPayStatus());
        order.setPayTime(new Date());
        order.setUpdateTime(new Date());

        //更新入库
        orderMapper.updateById(order);
    };

    /**
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay){
        OrderDO order=orderMapper.selectById(commandPay.getOrderId());
        if(Objects.isNull(order)){
            throw new RuntimeException("当前订单不存在");
        }
        if(!"0".equals(order.getPayStatus())){
            throw new RuntimeException("订单状态不为未支付状态");
        }
        order.setPayStatus(commandPay.getPayStatus());
        order.setUpdateTime(new Date());

        //更新入库
        int i=orderMapper.updateById(order);
        if(1>i){
            throw new RuntimeException("订单更新失败");
        }
        //库存释放
        inventoryDomainService.changeInventory(order.getSkuId(),null,null,order.getAmount());
    };
}
