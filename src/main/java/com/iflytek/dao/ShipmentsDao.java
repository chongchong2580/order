package com.iflytek.dao;

import com.iflytek.pojo.Shipments;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: chongchong
 * @DateTime: 2023/7/8 15:30
 * @Description:
 */
@Mapper
public interface ShipmentsDao {
    //发货
    @Insert("insert into shipments (logistics_companies,tracking_num,shipping_note,order_id) values (#{logisticsCompanies},#{trackingNum},#{shippingNote},#{orderId}) ")
    boolean addShipments(String logisticsCompanies,String trackingNum, String shippingNote, String orderId);

    //查询发货信息
    @Select("select * from shipments")
    List<Shipments> selectAll();
}
