package com.iflytek.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
* @Author: chongchong
* @Description: 前后端数据一致化， 通过状态码判断
* @DateTime: 2023/7/7 1:27
* @Params: result 数据处理结果
* @Return result 处理结果
*/

public enum T {
    SUESS_CODE("200"),
    ERROR_CODE("500"),
    SUESS("成功"),
    ERROR("失败");

    @EnumValue
    private String result;

    T(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
