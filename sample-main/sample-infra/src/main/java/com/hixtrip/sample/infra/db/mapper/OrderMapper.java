package com.hixtrip.sample.infra.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.infra.db.dataobject.OrderDo;

/**
 * @author lmk
 * @create 2024/3/7 16:39
 */
public interface OrderMapper extends BaseMapper<OrderDo> {
}
