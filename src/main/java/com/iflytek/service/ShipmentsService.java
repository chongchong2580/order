package com.iflytek.service;

import com.iflytek.pojo.Shipments;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: chongchong
 * @DateTime: 2023/7/8 15:30
 * @Description:
 */
public interface ShipmentsService {
    //发货
    boolean addShipments(String logisticsCompanies,String trackingNum, String shippingNote, String orderId);

    //查询发货信息
    List<Shipments> selectAll();
}
