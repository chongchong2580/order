package com.iflytek.service.impl;

import com.iflytek.dao.ProductDao;
import com.iflytek.pojo.Product;
import com.iflytek.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: chongchong
 * @DateTime: 2023/7/11 22:02
 * @Description:
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductDao productDao;

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public boolean addProduct(Product product) {
        return productDao.addProduct(product);
    }

    @Override
    public List<Product> findLast() {
        return productDao.findLast();
    }

    @Override
    public boolean removeProduct(String productId) {
        return productDao.removeProduct(productId);
    }
}
