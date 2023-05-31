package com.hixtrip.sample.infra.db.mapper;

import com.hixtrip.sample.infra.db.dataobject.OrderDO;

public interface OrderDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderDO record);

    int insertSelective(OrderDO record);

    OrderDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDO record);

    int updateByPrimaryKey(OrderDO record);
}