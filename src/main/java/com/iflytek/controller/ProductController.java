package com.iflytek.controller;

import com.iflytek.enums.Result;
import com.iflytek.enums.T;
import com.iflytek.pojo.Product;
import com.iflytek.service.OrderService;
import com.iflytek.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: chongchong
 * @DateTime: 2023/7/11 22:03
 * @Description:
 */
@RestController
@Api(value = "商品管理",tags = {"商品管理接口"})
@RequestMapping("product")
public class ProductController {
    @Resource
    private ProductService productService;

    @ApiOperation(value = "查询商品信息")
    @GetMapping("/findAll")
    public Result findAll() {
        List<Product> products = productService.findAll();
        if (products != null) {
            return new Result(T.SUESS_CODE.getResult(), T.SUESS.getResult(), products);
        } else {
            return new Result(T.ERROR_CODE.getResult(), T.ERROR.getResult(), products);
        }
    }
    @ApiOperation(value = "新增商品信息")
    @PostMapping("/add")
    public Result addProduct(@RequestBody Product product) {
        boolean flag = productService.addProduct(product);
        List<Product> products = productService.findLast();
        if (flag) {
            return new Result(T.SUESS_CODE.getResult(), T.SUESS.getResult(), products);
        } else {
            return new Result(T.ERROR_CODE.getResult(), T.ERROR.getResult(), products);
        }
    }
    @ApiOperation(value = "删除商品信息")
    @DeleteMapping("/remove")
    public Result removeProduct(@RequestParam String productId) {
        boolean flag = productService.removeProduct(productId);
        List<Product> products = productService.findAll();
        if (flag) {
            return new Result(T.SUESS_CODE.getResult(), T.SUESS.getResult(), products);
        } else {
            return new Result(T.ERROR_CODE.getResult(), T.ERROR.getResult(), products);
        }
    }
}
