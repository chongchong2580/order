package com.iflytek.service;

import com.iflytek.pojo.Order;
import com.iflytek.pojo.Product;
import com.iflytek.pojo.Shipments;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @Author: chongchong
 * @DateTime: 2023/7/7 1:58
 * @Description:
 */
public interface OrderService {

    Map<String,Object> findTotal(String productId);

    //在添加数据的同时存储产品货号这个标识到订单表中，为发货表进行后续处理提供产品依据
    boolean addProduct(String productId);

    //查询某一个数据，测试解密
    Order getOrder(String orderId);

    //查询新增的订单信息
    List<Order> findAll();

    //新增订单数据
    boolean addOrder(Order order);

    //发货完成后更改订单表中订单状态，代发货--》已发货
    boolean updateOrderStatus(String orderId, Integer orderStatus);


    //发货页面基本信息查询
    List<Order> selectOrder(String orderId);

    //模糊查询
    List<Order> like(Long orderStatus, String orderId, String phone, String orderTime);

    //查询总条数数据
    int selectTotal();
    //分页查询
    List<Order> selectPage(Integer pageNumber, Integer pageSize);
    //查询列表数据未脱敏
    List<Map<String,Object>> findOrder();
}
