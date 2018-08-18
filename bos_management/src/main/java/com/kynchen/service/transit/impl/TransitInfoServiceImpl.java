package com.kynchen.service.transit.impl;/*
* @author kynchen
* 
* @date 2018/8/15 10:29
*
* @version idea
*/

import bos.domain.take_delivery.WayBill;
import bos.domain.transit.TransitInfo;
import com.kynchen.dao.take_delivery.WayBillRepository;
import com.kynchen.dao.transit.TransitInfoRepository;
import com.kynchen.elastic.WayBillElasticRepository;
import com.kynchen.service.transit.TransitInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransitInfoServiceImpl implements TransitInfoService {

    @Autowired
    private TransitInfoRepository transitInfoRepository;
    @Autowired
    private WayBillElasticRepository wayBillElasticRepository;
    @Autowired
    private WayBillRepository wayBillRepository;


    @Override
    public void create(String wayBillIds) {
        if(StringUtils.isNotBlank(wayBillIds)) {
            String[] ids = wayBillIds.split(",");
            for (String wayBillId : ids) {
                WayBill wayBill = wayBillRepository.findOne(Integer.parseInt(wayBillId));
                //判断是否为待发货 1为待发货
                if(wayBill.getSignStatus()==1){
                    TransitInfo transitInfo = new TransitInfo();
                    transitInfo.setWayBill(wayBill);
                    transitInfo.setStatus("出入库中转");
                    transitInfoRepository.save(transitInfo);
                    //修改运单状态
                    wayBill.setSignStatus(2);
                    wayBillElasticRepository.save(wayBill);
                }
            }
        }
    }

    @Override
    public Page<TransitInfo> page_query(Pageable pageable) {
        return transitInfoRepository.findAll(pageable);
    }
}
