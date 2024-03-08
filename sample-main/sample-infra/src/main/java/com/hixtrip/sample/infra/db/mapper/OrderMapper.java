package com.hixtrip.sample.infra.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * mapper示例
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {

    void creatOrder(OrderDO orderDO);

    void updateOrder(OrderDO orderDO);

    OrderDO qryOrderById(@Param("orderId") String orderId);
}
