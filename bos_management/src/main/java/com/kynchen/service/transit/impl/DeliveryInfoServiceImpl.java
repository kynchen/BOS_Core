package com.kynchen.service.transit.impl;/*
* @author kynchen
* 
* @date 2018/8/15 21:50
*
* @version idea
*/

import bos.domain.transit.DeliveryInfo;
import bos.domain.transit.TransitInfo;
import com.kynchen.dao.transit.DeliveryInfoRepository;
import com.kynchen.dao.transit.TransitInfoRepository;
import com.kynchen.service.transit.DeliveryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeliveryInfoServiceImpl implements DeliveryInfoService {

    @Autowired
    private DeliveryInfoRepository deliveryInfoRepository;
    @Autowired
    private TransitInfoRepository transitInfoRepository;
    @Override
    public void save(String transitInfoId, DeliveryInfo deliveryInfo) {
        //保存配送信息
        deliveryInfoRepository.save(deliveryInfo);

        TransitInfo transitInfo = transitInfoRepository.findOne(Integer.parseInt(transitInfoId));
        //修改运输状态信息
        transitInfo.setStatus("开始配送");
        //关联配送信息
        transitInfo.setDeliveryInfo(deliveryInfo);
    }
}
