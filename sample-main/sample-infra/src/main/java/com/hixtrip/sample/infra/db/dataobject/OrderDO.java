package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*****
 * @Author:
 * @Description:
 ****/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "order")
public class OrderDO extends SampleDO implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String payType;
    private Date payTime;
    private Date consignTime;
    private Date endTime;
    private String username;
    private String recipients;  //收件人
    private String recipientsMobile; //收件人手机号
    private String recipientsAddress; //收件人地址
    private String weixinTransactionId;
    private Integer totalNum;   //订单商品总数量
    private Integer moneys;     //支付总金额
    private Integer orderStatus;
    private Integer payStatus;

    //购物车ID集合
    @TableField(exist = false)
    private List<String> cartIds;
}