package com.hixtrip.sample.infra.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单Mapper
 * @author gongjs
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {
}
