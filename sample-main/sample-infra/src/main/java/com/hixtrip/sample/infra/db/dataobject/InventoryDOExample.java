package com.hixtrip.sample.infra.db.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventoryDOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InventoryDOExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Long value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Long value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Long value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Long value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Long value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Long value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Long> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Long> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Long value1, Long value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Long value1, Long value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andCreatoridIsNull() {
            addCriterion("creatorId is null");
            return (Criteria) this;
        }

        public Criteria andCreatoridIsNotNull() {
            addCriterion("creatorId is not null");
            return (Criteria) this;
        }

        public Criteria andCreatoridEqualTo(Long value) {
            addCriterion("creatorId =", value, "creatorid");
            return (Criteria) this;
        }

        public Criteria andCreatoridNotEqualTo(Long value) {
            addCriterion("creatorId <>", value, "creatorid");
            return (Criteria) this;
        }

        public Criteria andCreatoridGreaterThan(Long value) {
            addCriterion("creatorId >", value, "creatorid");
            return (Criteria) this;
        }

        public Criteria andCreatoridGreaterThanOrEqualTo(Long value) {
            addCriterion("creatorId >=", value, "creatorid");
            return (Criteria) this;
        }

        public Criteria andCreatoridLessThan(Long value) {
            addCriterion("creatorId <", value, "creatorid");
            return (Criteria) this;
        }

        public Criteria andCreatoridLessThanOrEqualTo(Long value) {
            addCriterion("creatorId <=", value, "creatorid");
            return (Criteria) this;
        }

        public Criteria andCreatoridIn(List<Long> values) {
            addCriterion("creatorId in", values, "creatorid");
            return (Criteria) this;
        }

        public Criteria andCreatoridNotIn(List<Long> values) {
            addCriterion("creatorId not in", values, "creatorid");
            return (Criteria) this;
        }

        public Criteria andCreatoridBetween(Long value1, Long value2) {
            addCriterion("creatorId between", value1, value2, "creatorid");
            return (Criteria) this;
        }

        public Criteria andCreatoridNotBetween(Long value1, Long value2) {
            addCriterion("creatorId not between", value1, value2, "creatorid");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeIsNull() {
            addCriterion("createdTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeIsNotNull() {
            addCriterion("createdTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeEqualTo(Date value) {
            addCriterion("createdTime =", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeNotEqualTo(Date value) {
            addCriterion("createdTime <>", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeGreaterThan(Date value) {
            addCriterion("createdTime >", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createdTime >=", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeLessThan(Date value) {
            addCriterion("createdTime <", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeLessThanOrEqualTo(Date value) {
            addCriterion("createdTime <=", value, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeIn(List<Date> values) {
            addCriterion("createdTime in", values, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeNotIn(List<Date> values) {
            addCriterion("createdTime not in", values, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeBetween(Date value1, Date value2) {
            addCriterion("createdTime between", value1, value2, "createdtime");
            return (Criteria) this;
        }

        public Criteria andCreatedtimeNotBetween(Date value1, Date value2) {
            addCriterion("createdTime not between", value1, value2, "createdtime");
            return (Criteria) this;
        }

        public Criteria andModifieridIsNull() {
            addCriterion("modifierId is null");
            return (Criteria) this;
        }

        public Criteria andModifieridIsNotNull() {
            addCriterion("modifierId is not null");
            return (Criteria) this;
        }

        public Criteria andModifieridEqualTo(Long value) {
            addCriterion("modifierId =", value, "modifierid");
            return (Criteria) this;
        }

        public Criteria andModifieridNotEqualTo(Long value) {
            addCriterion("modifierId <>", value, "modifierid");
            return (Criteria) this;
        }

        public Criteria andModifieridGreaterThan(Long value) {
            addCriterion("modifierId >", value, "modifierid");
            return (Criteria) this;
        }

        public Criteria andModifieridGreaterThanOrEqualTo(Long value) {
            addCriterion("modifierId >=", value, "modifierid");
            return (Criteria) this;
        }

        public Criteria andModifieridLessThan(Long value) {
            addCriterion("modifierId <", value, "modifierid");
            return (Criteria) this;
        }

        public Criteria andModifieridLessThanOrEqualTo(Long value) {
            addCriterion("modifierId <=", value, "modifierid");
            return (Criteria) this;
        }

        public Criteria andModifieridIn(List<Long> values) {
            addCriterion("modifierId in", values, "modifierid");
            return (Criteria) this;
        }

        public Criteria andModifieridNotIn(List<Long> values) {
            addCriterion("modifierId not in", values, "modifierid");
            return (Criteria) this;
        }

        public Criteria andModifieridBetween(Long value1, Long value2) {
            addCriterion("modifierId between", value1, value2, "modifierid");
            return (Criteria) this;
        }

        public Criteria andModifieridNotBetween(Long value1, Long value2) {
            addCriterion("modifierId not between", value1, value2, "modifierid");
            return (Criteria) this;
        }

        public Criteria andModifiedtimeIsNull() {
            addCriterion("modifiedTime is null");
            return (Criteria) this;
        }

        public Criteria andModifiedtimeIsNotNull() {
            addCriterion("modifiedTime is not null");
            return (Criteria) this;
        }

        public Criteria andModifiedtimeEqualTo(Date value) {
            addCriterion("modifiedTime =", value, "modifiedtime");
            return (Criteria) this;
        }

        public Criteria andModifiedtimeNotEqualTo(Date value) {
            addCriterion("modifiedTime <>", value, "modifiedtime");
            return (Criteria) this;
        }

        public Criteria andModifiedtimeGreaterThan(Date value) {
            addCriterion("modifiedTime >", value, "modifiedtime");
            return (Criteria) this;
        }

        public Criteria andModifiedtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modifiedTime >=", value, "modifiedtime");
            return (Criteria) this;
        }

        public Criteria andModifiedtimeLessThan(Date value) {
            addCriterion("modifiedTime <", value, "modifiedtime");
            return (Criteria) this;
        }

        public Criteria andModifiedtimeLessThanOrEqualTo(Date value) {
            addCriterion("modifiedTime <=", value, "modifiedtime");
            return (Criteria) this;
        }

        public Criteria andModifiedtimeIn(List<Date> values) {
            addCriterion("modifiedTime in", values, "modifiedtime");
            return (Criteria) this;
        }

        public Criteria andModifiedtimeNotIn(List<Date> values) {
            addCriterion("modifiedTime not in", values, "modifiedtime");
            return (Criteria) this;
        }

        public Criteria andModifiedtimeBetween(Date value1, Date value2) {
            addCriterion("modifiedTime between", value1, value2, "modifiedtime");
            return (Criteria) this;
        }

        public Criteria andModifiedtimeNotBetween(Date value1, Date value2) {
            addCriterion("modifiedTime not between", value1, value2, "modifiedtime");
            return (Criteria) this;
        }

        public Criteria andSkuIdIsNull() {
            addCriterion("sku_id is null");
            return (Criteria) this;
        }

        public Criteria andSkuIdIsNotNull() {
            addCriterion("sku_id is not null");
            return (Criteria) this;
        }

        public Criteria andSkuIdEqualTo(Long value) {
            addCriterion("sku_id =", value, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdNotEqualTo(Long value) {
            addCriterion("sku_id <>", value, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdGreaterThan(Long value) {
            addCriterion("sku_id >", value, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdGreaterThanOrEqualTo(Long value) {
            addCriterion("sku_id >=", value, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdLessThan(Long value) {
            addCriterion("sku_id <", value, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdLessThanOrEqualTo(Long value) {
            addCriterion("sku_id <=", value, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdIn(List<Long> values) {
            addCriterion("sku_id in", values, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdNotIn(List<Long> values) {
            addCriterion("sku_id not in", values, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdBetween(Long value1, Long value2) {
            addCriterion("sku_id between", value1, value2, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdNotBetween(Long value1, Long value2) {
            addCriterion("sku_id not between", value1, value2, "skuId");
            return (Criteria) this;
        }

        public Criteria andSellableQtyIsNull() {
            addCriterion("sellable_qty is null");
            return (Criteria) this;
        }

        public Criteria andSellableQtyIsNotNull() {
            addCriterion("sellable_qty is not null");
            return (Criteria) this;
        }

        public Criteria andSellableQtyEqualTo(Long value) {
            addCriterion("sellable_qty =", value, "sellableQty");
            return (Criteria) this;
        }

        public Criteria andSellableQtyNotEqualTo(Long value) {
            addCriterion("sellable_qty <>", value, "sellableQty");
            return (Criteria) this;
        }

        public Criteria andSellableQtyGreaterThan(Long value) {
            addCriterion("sellable_qty >", value, "sellableQty");
            return (Criteria) this;
        }

        public Criteria andSellableQtyGreaterThanOrEqualTo(Long value) {
            addCriterion("sellable_qty >=", value, "sellableQty");
            return (Criteria) this;
        }

        public Criteria andSellableQtyLessThan(Long value) {
            addCriterion("sellable_qty <", value, "sellableQty");
            return (Criteria) this;
        }

        public Criteria andSellableQtyLessThanOrEqualTo(Long value) {
            addCriterion("sellable_qty <=", value, "sellableQty");
            return (Criteria) this;
        }

        public Criteria andSellableQtyIn(List<Long> values) {
            addCriterion("sellable_qty in", values, "sellableQty");
            return (Criteria) this;
        }

        public Criteria andSellableQtyNotIn(List<Long> values) {
            addCriterion("sellable_qty not in", values, "sellableQty");
            return (Criteria) this;
        }

        public Criteria andSellableQtyBetween(Long value1, Long value2) {
            addCriterion("sellable_qty between", value1, value2, "sellableQty");
            return (Criteria) this;
        }

        public Criteria andSellableQtyNotBetween(Long value1, Long value2) {
            addCriterion("sellable_qty not between", value1, value2, "sellableQty");
            return (Criteria) this;
        }

        public Criteria andWithholdingQtyIsNull() {
            addCriterion("withholding_qty is null");
            return (Criteria) this;
        }

        public Criteria andWithholdingQtyIsNotNull() {
            addCriterion("withholding_qty is not null");
            return (Criteria) this;
        }

        public Criteria andWithholdingQtyEqualTo(Long value) {
            addCriterion("withholding_qty =", value, "withholdingQty");
            return (Criteria) this;
        }

        public Criteria andWithholdingQtyNotEqualTo(Long value) {
            addCriterion("withholding_qty <>", value, "withholdingQty");
            return (Criteria) this;
        }

        public Criteria andWithholdingQtyGreaterThan(Long value) {
            addCriterion("withholding_qty >", value, "withholdingQty");
            return (Criteria) this;
        }

        public Criteria andWithholdingQtyGreaterThanOrEqualTo(Long value) {
            addCriterion("withholding_qty >=", value, "withholdingQty");
            return (Criteria) this;
        }

        public Criteria andWithholdingQtyLessThan(Long value) {
            addCriterion("withholding_qty <", value, "withholdingQty");
            return (Criteria) this;
        }

        public Criteria andWithholdingQtyLessThanOrEqualTo(Long value) {
            addCriterion("withholding_qty <=", value, "withholdingQty");
            return (Criteria) this;
        }

        public Criteria andWithholdingQtyIn(List<Long> values) {
            addCriterion("withholding_qty in", values, "withholdingQty");
            return (Criteria) this;
        }

        public Criteria andWithholdingQtyNotIn(List<Long> values) {
            addCriterion("withholding_qty not in", values, "withholdingQty");
            return (Criteria) this;
        }

        public Criteria andWithholdingQtyBetween(Long value1, Long value2) {
            addCriterion("withholding_qty between", value1, value2, "withholdingQty");
            return (Criteria) this;
        }

        public Criteria andWithholdingQtyNotBetween(Long value1, Long value2) {
            addCriterion("withholding_qty not between", value1, value2, "withholdingQty");
            return (Criteria) this;
        }

        public Criteria andOccupiedQtyIsNull() {
            addCriterion("occupied_qty is null");
            return (Criteria) this;
        }

        public Criteria andOccupiedQtyIsNotNull() {
            addCriterion("occupied_qty is not null");
            return (Criteria) this;
        }

        public Criteria andOccupiedQtyEqualTo(Long value) {
            addCriterion("occupied_qty =", value, "occupiedQty");
            return (Criteria) this;
        }

        public Criteria andOccupiedQtyNotEqualTo(Long value) {
            addCriterion("occupied_qty <>", value, "occupiedQty");
            return (Criteria) this;
        }

        public Criteria andOccupiedQtyGreaterThan(Long value) {
            addCriterion("occupied_qty >", value, "occupiedQty");
            return (Criteria) this;
        }

        public Criteria andOccupiedQtyGreaterThanOrEqualTo(Long value) {
            addCriterion("occupied_qty >=", value, "occupiedQty");
            return (Criteria) this;
        }

        public Criteria andOccupiedQtyLessThan(Long value) {
            addCriterion("occupied_qty <", value, "occupiedQty");
            return (Criteria) this;
        }

        public Criteria andOccupiedQtyLessThanOrEqualTo(Long value) {
            addCriterion("occupied_qty <=", value, "occupiedQty");
            return (Criteria) this;
        }

        public Criteria andOccupiedQtyIn(List<Long> values) {
            addCriterion("occupied_qty in", values, "occupiedQty");
            return (Criteria) this;
        }

        public Criteria andOccupiedQtyNotIn(List<Long> values) {
            addCriterion("occupied_qty not in", values, "occupiedQty");
            return (Criteria) this;
        }

        public Criteria andOccupiedQtyBetween(Long value1, Long value2) {
            addCriterion("occupied_qty between", value1, value2, "occupiedQty");
            return (Criteria) this;
        }

        public Criteria andOccupiedQtyNotBetween(Long value1, Long value2) {
            addCriterion("occupied_qty not between", value1, value2, "occupiedQty");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}