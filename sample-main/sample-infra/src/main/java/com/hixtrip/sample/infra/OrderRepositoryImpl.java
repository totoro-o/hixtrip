package com.hixtrip.sample.infra;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Cart;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.dataobject.OrderSkuDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import com.hixtrip.sample.infra.db.mapper.OrderSkuMapper;
import com.hixtrip.sample.infra.db.mapper.SkuMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class OrderRepositoryImpl  implements OrderRepository {

    @Resource
    private SkuMapper skuMapper;
    @Resource
    private OrderSkuMapper orderSkuMapper;
    @Resource
    private OrderMapper orderMapper;

    @Override
    public Boolean createOrder(Order order) {

        //数据完善
        order.setId(IdWorker.getIdStr());   //ID

        //1、查询购物车数据 通过order.getCartIds()
        List<Cart> carts = new ArrayList<>();
        if(carts==null || carts.size()==0){
            return false;
        }

        //2、递减库存
        this.dcount(carts);

        //3、添加订单明细
        int totalNum=0;
        int moneys = 0;
        for (Cart cart : carts) {
            //将Cart转成OrderSku
            OrderSkuDO orderSku = new OrderSkuDO();
            BeanUtils.copyProperties(cart, orderSku);
            orderSku.setId(IdWorker.getIdStr());
            orderSku.setOrderId(order.getId()); //提前赋值
            orderSku.setMoney(orderSku.getPrice()*orderSku.getNum());

            //添加
            orderSkuMapper.insert(orderSku);

            //统计计算
            totalNum +=orderSku.getNum();
            moneys += orderSku.getMoney();
        }

        //4、添加订单
        order.setTotalNum(totalNum);
        order.setMoneys(moneys);
        orderMapper.insert(order);

        //5、删除购物车数据 todo

        return true;
    }

    @Override
    public void dcount(List<Cart> carts) {
        for (Cart cart : carts) {
            //库存递减
            int dcount = skuMapper.dcount(cart.getSkuId(), cart.getNum());
            if(dcount<=0){
                throw new RuntimeException("库存不足！");
            }
        }
    }

    @Override
    public int updateAfterPayStatus(String id) {

        //修改后的状态
        Order order = Order.builder().build();
        order.setId(id);
        order.setOrderStatus(1);    // 待发货
        order.setPayStatus(1);  //已支付

        //修改条件
        QueryWrapper<Order> queryWrapper = new QueryWrapper<Order>();
        queryWrapper.eq("id",id);
        queryWrapper.eq("order_status",0);
        queryWrapper.eq("pay_status",0);
        return orderMapper.update(order,queryWrapper);
    }


}
