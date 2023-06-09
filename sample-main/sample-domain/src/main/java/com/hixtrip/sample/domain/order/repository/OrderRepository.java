package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.em.EnumOrderStatus;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.model.OrderItem;

import java.util.List;

public interface OrderRepository {

    Order findById(Long orderId);

    List<OrderItem> findOrderItemList(Long orderId);

    /**
     * 生成订单号 格式为 yyyyMMddHHmmssSSS+4位随机数+用户ID后两位
     */
    String createOrderNo(Long userId);

    /**
     * 查询订单号是否存在
     *
     * @param orderNo
     * @return
     */
    boolean existOrderNo(String orderNo);

    /**
     * 计算支付金额
     *
     * @param userId
     * @param orderItemList
     */
    Long calcPayAmount(Long userId, List<OrderItem> orderItemList);

    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    boolean insertIfNotExist(Order order);


    /**
     * 创建订单明细
     *
     * @param orderItemList
     */
    void createItemList(List<OrderItem> orderItemList);

    /**
     * 变更支付状态
     *
     * @param orderId
     * @param sourceOrderStatus
     * @param afterOrderStatus
     * @return
     */
    boolean updateOrderStatus(Long orderId, Integer sourceOrderStatus, Integer afterOrderStatus);

}
