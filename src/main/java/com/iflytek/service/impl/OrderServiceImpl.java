package com.iflytek.service.impl;

import com.iflytek.dao.OrderDao;
import com.iflytek.pojo.Order;
import com.iflytek.pojo.Product;
import com.iflytek.pojo.Shipments;
import com.iflytek.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: chongchong
 * @DateTime: 2023/7/7 1:58
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Override
    public boolean addOrder(Order order) {
        return orderDao.addOrder(order);
    }

    //发货完成后更改订单表中订单状态，代发货--》已发货
    @Override
    public boolean updateOrderStatus(String orderId, Integer orderStatus) {
        return orderDao.updateOrderStatus(orderId, orderStatus);
    }


    //发货页面基本信息查询
    @Override
    public List<Order> selectOrder(String orderId) {
        return orderDao.selectOrder(orderId);
    }

    @Override
    public List<Order> like(Long orderStatus, String orderId, String phone, String orderTime) {
        return orderDao.like(orderStatus, orderId, phone, orderTime);
    }

    //查询总条数数据
    @Override
    public int selectTotal() {
        return orderDao.selectTotal();
    }
    //分页查询
    @Override
    public List<Order> selectPage(Integer pageNumber, Integer pageSize) {
        return orderDao.selectPage(pageNumber, pageSize);
    }
    //查询列表数据未脱敏
    @Override
    public List<Map<String,Object>> findOrder() {
        return orderDao.findOrder();
    }

    @Override
    public Map<String,Object> findTotal(String productId) {
        return orderDao.findTotal(productId);
    }

    //在添加数据的同时存储产品货号这个标识到订单表中，为发货表进行后续处理提供产品依据
    @Override
    public boolean addProduct(String productId) {
        return orderDao.addProduct(productId);
    }

    //查询某一个数据，测试解密
    @Override
    public Order getOrder(String orderId) {
        return orderDao.getOrder(orderId);
    }

    //查询新增的订单信息
    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }
}
