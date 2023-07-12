package com.iflytek.dao;

import com.iflytek.pojo.Order;
import com.iflytek.pojo.Product;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: chongchong
 * @DateTime: 2023/7/11 22:00
 * @Description:
 */
@Mapper
public interface ProductDao {
    //查询商品
    @Select("select * from product")
    List<Product> findAll();

    //添加商品
    @Insert("insert into product (product_img,product_name,product_id,product_size,product_price,product_num) " +
            "values (#{productImg},#{productName},#{productId},#{productSize},#{productPrice},#{productNum})")
    boolean addProduct(Product product);

    //查询新增的商品信息
    @Select("select * from product where id = (select max(id) from product)")
    List<Product> findLast();

    //删除商品
    @Delete("delete from product where product_id = #{productId}")
    boolean removeProduct(String productId);
}
