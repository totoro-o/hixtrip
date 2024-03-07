package com.hixtrip.sample.domain.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 商品库存
 *
 * @author gongjs
 * @date 2024/3/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Inventory {

    private String skuId;

    /**
     * 可售库存
     */
    private Long sellableQuantity;

    /**
     * 预占库存
     */
    private Long withholdingQuantity;

    /**
     * 占用库存
     */
    private Long occupiedQuantity;

    /**
     * 操作的预占库存数量
     */
    private Long withholdingNum = 0L;

    /**
     * 操作的占用库存数量
     */
    private Long occupiedNum = 0L;

    private Boolean withholdingFlag = false;

    private Boolean occupiedFlag = false;

    /**
     * 预占库存操作
     *
     * @param num 预占库存数量
     * @return
     */
    public Boolean withholding(long num) {
        //可售库存必须大于预占库存数量
        if (Boolean.FALSE.equals(occupiedFlag) && sellableQuantity >= num) {
            withholdingNum = num;
            withholdingQuantity += num;
            withholdingFlag = true;
            return true;
        }
        return false;
    }

    /**
     * 占用库存操作
     *
     * @param num 占用库存数量
     * @return
     */
    public Boolean occupied(long num) {
        //刚才的预占库存数量要等于占用库存数量
        if (Boolean.FALSE.equals(withholdingFlag) && withholdingNum == num) {
            withholdingNum = 0L;
            occupiedNum += num;
            occupiedFlag = true;
            return true;
        }
        return false;
    }

}
