package com.hixtrip.sample.infra.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * mapper示例
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {

    int insertOrder(Order order);

}
