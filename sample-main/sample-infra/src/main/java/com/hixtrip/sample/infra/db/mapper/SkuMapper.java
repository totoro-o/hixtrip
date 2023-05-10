package com.hixtrip.sample.infra.db.mapper;

import com.baomidou.mybatisplus.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.infra.db.dataobject.SkuDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface SkuMapper extends BaseMapper<SkuDO> {

    /***
     * 库存递减
     */
    @Update("update sku set num=num-#{num} where id=#{id} and num>=#{num}")
    int dcount(@Param("id")String id, @Param("num")Integer num);

}
