package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "command_pay", autoResultMap = true)
@SuperBuilder(toBuilder = true)
public class CommandPayDO {

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 订单状态
     */
    private String payStatus;

}
