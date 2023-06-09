package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.em.EnumOrderStatus;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.model.OrderItem;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderMapper orderMapper;


    @Override
    public Order findById(Long orderId) {
//        OrderDO orderDO = orderMapper.findById(orderId);
        OrderDO orderDO = new OrderDO();
        return OrderDOConvertor.INSTANCE.doToDomain(orderDO);
    }

    @Override
    public List<OrderItem> findOrderItemList(Long orderId) {
        return null;
    }

    @Override
    public String createOrderNo(Long userId) {
        String userIdStr = String.valueOf(userId);
        while (true) {
            String orderNo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))
                    + String.valueOf(Math.random()).substring(2, 6)
                    + userIdStr.substring(userIdStr.length() - 2);
            // 数据库进行查询，判断订单号是否已存在，
            if (!this.existOrderNo(orderNo)) {
                return orderNo;
            }
        }
    }

    @Override
    public boolean existOrderNo(String orderNo) {
        return orderMapper.findOrderIdByCaseNo(orderNo) != null;
    }

    @Override
    public Long calcPayAmount(Long userId, List<OrderItem> orderItemList) {
        return orderItemList.stream().mapToLong(OrderItem::getCommodityTotalPrice).sum();
    }

    @Override
    public boolean insertIfNotExist(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.domainToDO(order);
        return orderMapper.insertIfNotExist(orderDO) > 0;
    }

    @Override
    public void createItemList(List<OrderItem> orderItemList) {
        // insert db
    }

    @Override
    public boolean updateOrderStatus(Long orderId, Integer sourceOrderStatus, Integer afterOrderStatus) {
        return orderMapper.updateOrderStatus(orderId, sourceOrderStatus, afterOrderStatus) > 0;
    }


}
