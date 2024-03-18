package com.hixtrip.sample.infra.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;


public interface OrderMapper extends BaseMapper<OrderDO> {


    OrderDO getOrder(String orderId);
}
