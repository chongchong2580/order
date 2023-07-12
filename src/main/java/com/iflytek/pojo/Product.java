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
 * @DateTime: 2023/7/7 1:06
 * @Description: 商品表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("product")
public class Product implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String productImg;
    private String productName;
    private String productId;
    private String productSize;
    private String productPrice;
    private int productNum;
}
