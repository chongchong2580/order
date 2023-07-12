package com.iflytek.controller;

import com.iflytek.enums.Result;
import com.iflytek.enums.T;
import com.iflytek.pojo.Order;
import com.iflytek.pojo.Shipments;
import com.iflytek.service.OrderService;
import com.iflytek.service.ShipmentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @Author: chongchong
 * @DateTime: 2023/7/8 15:41
 * @Description:
 */
@RestController
@Api(value = "发货管理",tags = {"发货管理接口"})
@RequestMapping("shipments")
public class ShipmentsController {
    @Resource
    private OrderService orderService;

    @Resource
    private ShipmentsService shipmentsService;

    @ApiOperation(value = "发货信息")
    @PostMapping("/add")
    public Result findShipments(@RequestParam String orderId,@RequestParam String logisticsCompanies,
                                @RequestParam String trackingNum,@RequestParam String shippingNote) {
        //存储查询到的用户基本信息
        List<Order> orders = orderService.selectOrder(orderId);
        boolean flag = shipmentsService.addShipments(logisticsCompanies,trackingNum,shippingNote,orderId);
        List<Shipments> shipmentsList = shipmentsService.selectAll();
        if (flag) {
            orderService.updateOrderStatus(orderId,2);
            return new Result(T.SUESS_CODE.getResult(), T.SUESS.getResult(), shipmentsList);
        } else {
            return new Result(T.ERROR_CODE.getResult(), T.ERROR.getResult(), shipmentsList);
        }
        /*if (orders.size() != 0) {
            return new Result(T.SUESS_CODE.getResult(), T.SUESS.getResult(), orders);
        } else {
            return new Result(T.ERROR_CODE.getResult(), T.ERROR.getResult(), orders);
        }*/
    }
}
