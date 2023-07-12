package com.iflytek.controller;

import com.iflytek.enums.OrderEnum;
import com.iflytek.enums.Result;
import com.iflytek.enums.SensitiveTypeEnum;
import com.iflytek.enums.T;
import com.iflytek.interfaces.NeedDecrypt;
import com.iflytek.interfaces.NeedEncrypt;
import com.iflytek.interfaces.Sensitive;
import com.iflytek.pojo.Order;
import com.iflytek.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: chongchong
 * @DateTime: 2023/7/7 1:55
 * @Description:
 */
@RestController
@Api(value = "订单管理",tags = {"订单管理接口"})
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @ApiOperation(value = "新增订单数据，加密")
    @PostMapping("/addOrder")
    @NeedEncrypt
    public Result addOrder(@RequestBody Order order,@RequestParam String productId) {
        Order o = new Order();
        //根据前端传回的商品号productId查询商品的单价和数量
        Map<String,Object> total = orderService.findTotal(productId);
        String price = (String) total.get("product_price");
        String num = total.get("product_num").toString();

        //获取当前新增订单的时间
        Date date = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //添加数据
        o.setOrderId(order.getOrderId());
        o.setOrderTotal((Double.parseDouble(price) * (Double.parseDouble(num) * 1.0)) + "");
        //订单新增默认状态为1：待发货状态
        o.setOrderStatus("1");
        o.setOrderTime(simpleDate.format(date));
        o.setConsignee(order.getConsignee());
        o.setPhone(order.getPhone());
        o.setAddress(order.getAddress());
        o.setOrderNote(order.getOrderNote());
        o.setProductId(productId);
        //新增
        boolean orders = orderService.addOrder(o);
        List<Order> ordersList = orderService.findAll();

        if (orders) {
            return new Result(T.SUESS_CODE.getResult(), T.SUESS.getResult(), ordersList);
        } else {
            return new Result(T.ERROR_CODE.getResult(), T.ERROR.getResult(), ordersList);
        }

    }

    /**
    * @Author: chongchong
    * @Description:  脱敏处理可以前端对字段进行处理，后端对字段加密处理解释，此处暂未使用后端进行处理
    * @DateTime: 2023/7/11 19:02
    * @Params: orderId
    * @Return order
    */
    @ApiOperation(value = "发货页面基本信息查询")
    @GetMapping("/selectOrder")
    @NeedDecrypt
    public List<Order> selectOrder(@RequestParam String orderId) throws NoSuchFieldException, IllegalAccessException {
        //存储查询到的用户基本信息
        List<Order> orders = orderService.selectOrder(orderId);

        Class<?> entityClass = Order.class;
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            Sensitive annotation = field.getAnnotation(Sensitive.class);
            if (annotation != null && field.equals("phone") || field.equals("consignee")) {
                try {
                    // 获取注解类中的属性值
                    Field typeField = annotation.getClass().getDeclaredField("type");
                    typeField.setAccessible(true);
                    // 将type属性的值设置为新的枚举值
                    typeField.set(annotation, SensitiveTypeEnum.CUSTOMER);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }



        /*List<Shipments> shipmentsList = shipmentsService.addShipments(shipments);
        if (shipmentsList != null) {
            orderService.updateOrderStatus(orderId,2);
            return new Result(T.ERROR_CODE.getResult(), T.ERROR.getResult(), shipmentsList);
        } else {
            return new Result(T.ERROR_CODE.getResult(), T.ERROR.getResult(), shipmentsList);
        }*/
        /*if (orders.size() != 0) {
            return new Result(T.SUESS_CODE.getResult(), T.SUESS.getResult(), orders);
        } else {
            return new Result(T.ERROR_CODE.getResult(), T.ERROR.getResult(), orders);
        }*/
        return orders;
    }


    @ApiOperation(value = "模糊查询列表数据")
    @GetMapping("/like")
    @NeedDecrypt
    public List<Order> findLike(@RequestParam(required = false,value = "orderStatus") Long orderStatus,
                           @RequestParam(required = false,value = "orderId") String orderId,
                           @RequestParam(required = false,value = "phone") String phone,
                           @RequestParam(required = false,value = "orderTime") String orderTime) {
//        return new Result(T.SUESS_CODE.getResult(), T.SUESS.getResult(), orderService.like(orderStatus, orderId, phone, orderTime));
        //模糊查询条件，获取模糊查询的结果
        List<Order> orders = orderService.like(orderStatus, orderId, phone, orderTime);
        //将模糊查询的结果，再通过枚举转换将发货状态从数字改变成文字
        orders.forEach(item -> {
            if (Integer.parseInt(item.getOrderStatus()) == OrderEnum.FINISHED.getNum()) {
                item.setOrderStatus(OrderEnum.FINISHED.getStatus());
            } else if (Integer.parseInt(item.getOrderStatus()) == OrderEnum.AFT_RECEIVE.getNum()) {
                item.setOrderStatus(OrderEnum.AFT_RECEIVE.getStatus());
            } else if (Integer.parseInt(item.getOrderStatus()) == OrderEnum.PRE_RECEIVE.getNum()) {
                item.setOrderStatus(OrderEnum.PRE_RECEIVE.getStatus());
            } else {
                item.setOrderStatus(OrderEnum.UNKNOWN.getStatus());
            }
        });
        return orders;
    }

    @ApiOperation(value = "查询某一个数据，测试解密")
    @GetMapping("/getOne")
    @NeedDecrypt
    public Order getOne(@RequestParam String orderId) {
        Order order = orderService.getOrder(orderId);
        return order;
    }

    @ApiOperation(value = "分页查询列表数据中总条数")
    @GetMapping("/total")
    public Result selectTotal() {
        Integer total = orderService.selectTotal();//数据总条数
        if (total != null) {
            return new Result(T.SUESS_CODE.getResult(),T.SUESS.getResult(),total);
        } else {
            return new Result(T.ERROR_CODE.getResult(),T.ERROR.getResult(),total);
        }
    }

    @ApiOperation(value = "分页查询列表数据")
    @GetMapping("/page")
    @NeedDecrypt//脱敏
    public List<Order> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;//每页第一条数据默认查询第一页
        List<Order> data = orderService.selectPage(pageNum,pageSize);//查询的数据
        Integer total = orderService.selectTotal();//数据总条数
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(res);
        data.forEach(item -> {
            if (Integer.parseInt(item.getOrderStatus()) == OrderEnum.FINISHED.getNum()) {
                item.setOrderStatus(OrderEnum.FINISHED.getStatus());
            } else if (Integer.parseInt(item.getOrderStatus()) == OrderEnum.AFT_RECEIVE.getNum()) {
                item.setOrderStatus(OrderEnum.AFT_RECEIVE.getStatus());
            } else if (Integer.parseInt(item.getOrderStatus()) == OrderEnum.PRE_RECEIVE.getNum()) {
                item.setOrderStatus(OrderEnum.PRE_RECEIVE.getStatus());
            } else {
                item.setOrderStatus(OrderEnum.UNKNOWN.getStatus());
            }
        });
//        return new Result(T.SUESS_CODE.getResult(),T.SUESS.getResult(), data);
        return data;
    }


    @ApiOperation(value = "查询列表全部数据，未解密脱敏，未分页")
    @GetMapping("/findList")
    public Result findList() {
        //接收查询的数据，在通过枚举转换
        List<Map<String,Object>> orders = orderService.findOrder();
        orders.forEach(item -> {
            //判断发货状态进行转换，当状态为0，1，2，3时分别对状态进行字符转换
          if (item.get("order_status").equals(OrderEnum.FINISHED.getNum())) {
              item.put("order_status", OrderEnum.FINISHED.getStatus());
          } else if (item.get("order_status").equals(OrderEnum.AFT_RECEIVE.getNum())) {
              item.put("order_status", OrderEnum.AFT_RECEIVE.getStatus());
          } else if (item.get("order_status").equals(OrderEnum.PRE_RECEIVE.getNum())) {
              item.put("order_status", OrderEnum.PRE_RECEIVE.getStatus());
          } else {
              item.put("order_status", OrderEnum.UNKNOWN.getStatus());
          }
        });
        // 查询出来的集合，不为空的时候添加进去
        if (!orders.isEmpty()) {
            return new Result(T.SUESS_CODE.getResult(),T.SUESS.getResult(),orders);
        } else {
            return new Result(T.ERROR_CODE.getResult(),T.ERROR.getResult(),orders);
        }
    }

}
