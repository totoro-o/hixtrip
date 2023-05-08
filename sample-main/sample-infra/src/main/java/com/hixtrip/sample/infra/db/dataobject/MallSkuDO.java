package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "mall-sku", autoResultMap = true)
@SuperBuilder(toBuilder = true)
public class MallSkuDO {
    private Long id;

    private String name;

    private String description;

    private Float price;

    private Date createTime;
}
