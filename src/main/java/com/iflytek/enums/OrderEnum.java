package com.iflytek.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
* @Author: chongchong
* @Description: 对订单状态进行枚举转换
* @DateTime: 2023/7/7 1:13
* @Params: num 订单状态
* @Return status 状态枚举
*/

public enum OrderEnum {
    UNKNOWN(0,"未知状态"),
    PRE_RECEIVE(1,"待发货"),
    AFT_RECEIVE(2,"已收货"),
    FINISHED(3,"已完成");
    @EnumValue
    private Integer num;
    @JsonValue
    private String status;

    OrderEnum(Integer num,String status) {
        this.num = num;
        this.status = status;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    /*public static List<String> getStatus(Integer status) {
        List<String> strings = new ArrayList<>();
        for (OrderEnum orderEnum: values()) {
            if (orderEnum.getNum().equals(status)) {
                strings.add(orderEnum.getStatus());
            }
        }
        return strings;
    }
    public static List<String> getStatus(List<Integer> status) {
        List<String> strings = new ArrayList<>();
        for (OrderEnum orderEnum: values()) {
            if (status.contains(orderEnum.getNum())) {
                strings.add(orderEnum.getStatus());
            }
        }
        return strings;
    }
    public static List<Integer> convert(List<String> strings) {
        List<Integer> list = new ArrayList<>();
        for (String str: strings) {
            for (OrderEnum status: OrderEnum.values()) {}
        }
    }*/
    public static OrderEnum convert(Integer num) {
        return Stream.of(values()).filter(bean -> bean.num.equals(num)).findAny().orElse(UNKNOWN);
    }
    public static OrderEnum convert(String status) {
        return Stream.of(values())
                .filter(bean -> bean.status.equals(status))
                .findAny()
                .orElse(UNKNOWN);
    }
}
