package com.hixtrip.sample.domain.inventory.model;

import com.hixtrip.sample.domain.BaseModel;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Inventory extends BaseModel<InventoryRepository> {

    //自增值
    private Long id;

    /**
     * skuid
     */
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
     * 并发版本号，控制并发
     */
    private long version;

    public Inventory getBySkuId(){
        return getBean().getBySkuId(this.skuId);
    }

    /**
     * 修改库存
     * @param withholdingQuantity
     * @return
     */
    public Integer changeInventory( Long withholdingQuantity) {
        //再次检查库存是否足够，不够返回
        if(this.sellableQuantity < withholdingQuantity){
            return 0;
        }
        //足够更新语句
        return getBean().changeInventory(this.skuId,this.sellableQuantity,withholdingQuantity,this.occupiedQuantity);
    }

    /**
     * 支付成功预占库存移到占用库存
     * @param withholdingQuantity
     * @return
     */
    public Integer changeIntentoryOnPaySuccess(Long withholdingQuantity){
        return getBean().changeIntentoryOnPaySuccess(this.skuId,withholdingQuantity);
    }

    /**
     * 支付失败预占库存移到可售库存
     * @param withholdingQuantity
     * @return
     */
    public Integer changeIntentoryOnPayFail(Long withholdingQuantity){
        return getBean().changeIntentoryOnPayFail(this.skuId,withholdingQuantity);
    }

    public Long getSellableQuantityBySkuId(){
        return getBySkuId().getSellableQuantity();
    }

    public InventoryRepository getBean(){
        return getBean("InventoryRepository");
    }


}
