package com.hixtrip.sample.infra.db.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {


    /**
     *  将订单更该为支付成功
     * @param orderId
     * @param payId
     * @param orderStatus
     * @param payStatus
     * @return
     */
    boolean orderToSuccess(@Param("orderId")String orderId, @Param("payId")String payId,
                           @Param("orderStatus") String orderStatus, @Param("payStatus")String payStatus);

    /**
     * 判断订单是否支付
     * @param orderId
     * @param payStatus
     * @return
     */
    boolean isNoPay(@Param("orderId") String orderId, @Param("payStatus")String payStatus);


}
