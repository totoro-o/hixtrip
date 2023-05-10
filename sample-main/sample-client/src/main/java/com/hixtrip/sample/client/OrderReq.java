package com.hixtrip.sample.client;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*****
 * @Author:
 * @Description:
 ****/
@Data
@Builder
public class OrderReq implements Serializable {

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
    private List<String> cartIds;
}