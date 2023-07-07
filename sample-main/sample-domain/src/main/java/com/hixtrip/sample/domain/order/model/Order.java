package com.hixtrip.sample.domain.order.model;

import com.hixtrip.sample.domain.BaseModel;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.order.annotation.OrderStatus;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
public class Order extends BaseModel<OrderRepository> {

    //自增值
    private Long id;
    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * userId
     */
    private String userId;

    /**
     * skuid
     */
    private String skuId;

    /**
     * sku价格
     */
    private BigDecimal skuPrice;

    /**
     * sku数量
     */
    private Long skuNumber;

    /**
     * 支付方式
     */
    private String payWay;
    /**
     * 支付金额
     */
    private BigDecimal totalPrice;
    /**
     * 实际支付金额
     */
    private BigDecimal payTotalPrice;
    /**
     * 订单状态
     */
    private String orderStatus;
    /**
     * 创建日期
     */
    private Date createTime;
    /**
     * 失效日期
     */
    private Date expireTime;
    /**
     * 支付日期
     */
    private Date payTime;

    /**
     * 删除标志
     */
    private String delFlag;

    /**
     * 创建订单
     * @param skuPrice
     * @return
     */
    public Order createOrder(BigDecimal skuPrice){
        // todo 这里的创建订单真实业务中还有记录订单详情，以及订单操作流水记录表持久化，在这边创建orderDetail，通过orderdetail去创建订单详情和流水记录
        this.skuPrice = skuPrice;
        this.totalPrice = skuPrice.multiply(new BigDecimal(String.valueOf(this.skuNumber)));
        this.createTime = new Date();
        this.orderNumber = UUID.randomUUID().toString().replace("-","");
        this.orderStatus = OrderStatus.NO_PAY.getCode();
        getBean().addOrder(this);
        return this;
    }

    /**
     * 获取订单
     * @param orderNumber
     * @return
     */
    public Order getOrderByOrderNumber(String orderNumber) {
        return getBean().getOrderByOrderNumber(orderNumber);
    }

    /**
     * 订单支付成功
     * @param order
     */
    public void orderToSuccess(Order order) {
        //todo 流水记录
        this.orderStatus = OrderStatus.PAY_SUCCESS.getCode();
        this.payTime = new Date();
        this.payTotalPrice = order.getPayTotalPrice();
        getBean().update(this);
    }

    /**
     * 订单支付失败
     */
    public void orderToFail() {
        //todo 流水记录
        this.orderStatus = OrderStatus.PAY_FAIL.getCode();
        getBean().update(this);
    }

    /**
     * 获取持久化层
     * @return
     */
    public OrderRepository getBean(){
        return getBean("OrderRepository");
    }



}
