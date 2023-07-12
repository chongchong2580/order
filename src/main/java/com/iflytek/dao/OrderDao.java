package com.iflytek.dao;

import com.iflytek.pojo.Order;
import com.iflytek.pojo.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: chongchong
 * @DateTime: 2023/7/7 1:52
 * @Description:
 */
@Mapper
public interface OrderDao {

    //新增订单后，前端点击某一个商品传回商品号，根据商品号查询该商品的单价和数量完成订单新增操作
    @Select("select product_price,product_num from product where product_id = #{productId}")
    Map<String,Object> findTotal(String productId);

    //在添加数据的同时存储产品货号这个标识到订单表中，为发货表进行后续处理提供产品依据
    @Insert("insert into orders (product_id) values #{productId} where id = (select max(id) from orders)")
    boolean addProduct(String productId);

    //查询某一个数据，测试解密
    @Select("select * from orders where order_id = #{orderId}")
    Order getOrder(String orderId);

    //查询新增的订单信息
    @Select("select * from orders where id = (select max(id) from orders)")
    List<Order> findAll();

    //新增订单
    @Insert("insert into orders (order_id,order_total,order_status,order_time,consignee,phone,address,order_note,product_id) " +
            " values (#{orderId}, #{orderTotal},#{orderStatus},#{orderTime},#{consignee},#{phone},#{address},#{orderNote},#{productId})")
    boolean addOrder(Order order);

    //发货完成后更改订单表中订单状态，代发货--》已发货
    @Update("update orders set order_status = #{orderStatus} where order_id = #{orderId}")
    boolean updateOrderStatus(String orderId, Integer orderStatus);

    //发货页面基本信息查询
    @Select("select * from orders where order_id = #{orderId}")
    List<Order> selectOrder(String orderId);

    //模糊查询
    /*@Select("select * from orders where order_id like '%${orderId}%' and phone like '%${phone}%' " +
            "and order_status like '%${orderStatus}%' and convert(order_time,DATETIME) like '${orderTime}%'")*/
    /*@Select("select * from orders where order_id like concat('%',#{orderId},'%') " +
            "and phone like concat('%',#{phone},'%') and order_status like concat('%',#{orderStatus},'%')" +
            "and convert(order_time,DATETIME) like concat('%',#{orderTime},'%')")*/
    List<Order> like(Long orderStatus, String orderId, String phone, String orderTime);

    //查询总条数数据
    @Select("select count(*) from orders")
    int selectTotal();

    //分页查询
    @Select("select * from orders limit #{pageNumber},#{pageSize}")
    List<Order> selectPage(Integer pageNumber, Integer pageSize);

    //查询列表数据未脱敏
    @Select("select order_id,order_total,order_status,order_time,consignee,phone from orders ")
    List<Map<String,Object>> findOrder();
}
