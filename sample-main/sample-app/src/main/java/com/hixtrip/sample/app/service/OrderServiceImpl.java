package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.InventoryConvertor;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.app.vo.OrderVo;
import com.hixtrip.sample.app.vo.PayCallBackVO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.annotation.OrderCallBack;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.PayDomainService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OrderServiceImpl implements OrderService, BeanPostProcessor {

    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Autowired
    private CommodityDomainService commodityDomainService;

    @Autowired
    private PayDomainService payDomainService;

    private Map<String, List<Object>> orderCallBackMap = new ConcurrentHashMap<>();


    /**
     * 创建订单，还有订单详情，订单操作流水要记录。
     * @param orderVo
     * @return
     * @throws Exception
     */
    @Override
    public boolean createOrder(OrderVo orderVo) throws Exception{
        Order order = OrderConvertor.INSTANCE.doToDomain(orderVo);
        Inventory inventory = InventoryConvertor.INSTANCE.doToDomain(orderVo);
        Inventory inventory1 = inventory.getBySkuId();
        //检查库存是否足够
        if(inventory1.getSellableQuantity() > orderVo.getSkuNumber()) {
            BigDecimal skuPrice = commodityDomainService.getSkuPrice(orderVo.getSkuId());
            //创建订单
            orderDomainService.createOrder(order, skuPrice);
            //修改库存
            if(inventoryDomainService.changeInventory(order.getSkuId(),order.getSkuNumber())){
                //失败抛出异常回滚事务
                throw new Exception("更改库存失败");
            };
            return true;
        }
        return false;
    }

    @Override
    public boolean payCallback(PayCallBackVO payCallBackVO) throws InvocationTargetException, IllegalAccessException {
        //记录支付回调数据，事务不用和下面一起，防止被回滚
        payDomainService.payRecord();
        /**
         * 通过回调的状态处理相关逻辑
         */
        List list = orderCallBackMap.get(payCallBackVO.getPayStatus());
        /**
         * 反射执行方法
         */
        return (Boolean) ((Method)list.get(1)).invoke(list.get(0),payCallBackVO.getParamStr());
    }

    /**
     * 后置处理器
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().getAnnotation(OrderCallBack.class)!=null){
            for(Method method : bean.getClass().getMethods()){
                OrderCallBack orderCallBack = method.getAnnotation(OrderCallBack.class);
                if(orderCallBack != null) {
                    List list = new ArrayList();
                    list.add(bean);
                    list.add(method);
                    orderCallBackMap.put(orderCallBack.value().getCode(),list);
                }

            }
        }
        return bean;
    }
}
