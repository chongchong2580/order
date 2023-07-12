package com.iflytek.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @Author: chongchong
 * @DateTime: 2023/7/7 1:25
 * @Description: 前后端数据一致化
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result {
    private String code;
    private String message;
    private Object data;
}
