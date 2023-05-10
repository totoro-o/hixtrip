package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/*****
 * @Author:
 * @Description:
 ****/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sku")
public class SkuDO implements Serializable{

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    private Integer price;
    private Integer num;
    private String image;
    private String images;
    private Date createTime;
    private Date updateTime;
    private String skuAttribute;
    private Integer status;
}