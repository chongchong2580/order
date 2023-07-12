package com.iflytek.service;

import com.iflytek.pojo.Product;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: chongchong
 * @DateTime: 2023/7/11 22:02
 * @Description:
 */
public interface ProductService {
    //查询商品
    List<Product> findAll();

    //添加商品
    boolean addProduct(Product product);

    //查询新增的商品信息
    List<Product> findLast();

    //删除商品
    boolean removeProduct(String productId);
}
