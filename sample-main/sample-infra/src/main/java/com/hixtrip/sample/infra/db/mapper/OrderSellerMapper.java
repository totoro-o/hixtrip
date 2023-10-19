package com.hixtrip.sample.infra.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.dataobject.OrderSellerDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> 订单卖家表
 *
 * @author airness
 * @since 2023/10/19 12:25
 */
@Mapper
public interface OrderSellerMapper extends BaseMapper<OrderSellerDO> {
}
