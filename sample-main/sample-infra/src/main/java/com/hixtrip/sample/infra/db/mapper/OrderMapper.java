package com.hixtrip.sample.infra.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 09:12
 * 订单DO实体Mapper对象
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {
}
