package com.hixtrip.sample.infra.db.dataobject;

import java.util.Date;

public class InventoryDO {
    private Long id;

    private Long version;

    private Long creatorid;

    private Date createdtime;

    private Long modifierid;

    private Date modifiedtime;

    private Long skuId;

    private Long sellableQty;

    private Long withholdingQty;

    private Long occupiedQty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Long creatorid) {
        this.creatorid = creatorid;
    }

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    public Long getModifierid() {
        return modifierid;
    }

    public void setModifierid(Long modifierid) {
        this.modifierid = modifierid;
    }

    public Date getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(Date modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getSellableQty() {
        return sellableQty;
    }

    public void setSellableQty(Long sellableQty) {
        this.sellableQty = sellableQty;
    }

    public Long getWithholdingQty() {
        return withholdingQty;
    }

    public void setWithholdingQty(Long withholdingQty) {
        this.withholdingQty = withholdingQty;
    }

    public Long getOccupiedQty() {
        return occupiedQty;
    }

    public void setOccupiedQty(Long occupiedQty) {
        this.occupiedQty = occupiedQty;
    }
}