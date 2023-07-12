package com.iflytek.service.impl;

import com.iflytek.dao.OrderDao;
import com.iflytek.dao.ShipmentsDao;
import com.iflytek.pojo.Shipments;
import com.iflytek.service.ShipmentsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: chongchong
 * @DateTime: 2023/7/8 15:31
 * @Description:
 */
@Service
public class ShipmentsServiceImpl implements ShipmentsService {

    @Resource
    private ShipmentsDao shipmentsDao;

    //发货
    @Override
    public boolean addShipments(String logisticsCompanies,String trackingNum, String shippingNote, String orderId) {
        return shipmentsDao.addShipments(logisticsCompanies,trackingNum,shippingNote,orderId);
    }
    //查询发货信息
    @Override
    public List<Shipments> selectAll() {
        return shipmentsDao.selectAll();
    }
}
