package com.hixtrip.sample.infra.db.dataobject;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author ljp
 * @since 2024-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order")
public class OrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
      @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 买家ID
     */
    private String userId;

    /**
     * 卖家ID
     */
    private String sellerId;

    /**
     * skuId
     */
    private String skuId;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * 购买金额
     */
    private BigDecimal money;

    /**
     * 购买时间
     */
    private LocalDateTime payTime;

    /**
     * 支付状态（NEW 未支付订单,PAY已经支付订单,CANCEL超时取消订单）
     */
    private String payStatus;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private Boolean delFlag;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String upLocalDateTimeBy;

    /**
     * 修改时间
     */
    private LocalDateTime upLocalDateTimeTime;

    /**
     * 支付类型，微信-银行-支付宝
     */
    private String payType;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 订单类型 DAILY普通单，PROMOTION促销订单
     */
    private String orderType;

    /**
     * 收货地址 json存储
     */
    private String receiverAddress;


}
