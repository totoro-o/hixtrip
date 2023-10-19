package com.hixtrip.sample.infra.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> 订单买家表
 *
 * @author airness
 * @since 2023/10/19 12:25
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {
}
