package com.iflytek.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iflytek.enums.SensitiveTypeEnum;
import com.iflytek.interfaces.EncryptField;
import com.iflytek.interfaces.Sensitive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Author: chongchong
 * @DateTime: 2023/7/7 0:55
 * @Description: 订单表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "orders",autoResultMap = true)
@Accessors(chain = true)
public class Order implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String orderId;//订单号
    private String orderTotal;//订单总额
    private String orderStatus;//订单状态
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String orderTime;//下单时间
    @EncryptField
    @Sensitive(type = SensitiveTypeEnum.NAME)
    private String consignee;//收货人
    @EncryptField
    @Sensitive(type = SensitiveTypeEnum.PHONE_NUM)
    private String phone;//收货电话号码
    @EncryptField
    private String address;//收货地址
    private String orderNote;//收货备注
    private String productId;//货号
}
