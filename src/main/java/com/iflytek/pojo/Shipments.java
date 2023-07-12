package com.iflytek.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: chongchong
 * @DateTime: 2023/7/7 1:08
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("shipments")
public class Shipments implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String logisticsCompanies;
    private String trackingNum;
    private String shippingNote;
    private String orderId;
}
