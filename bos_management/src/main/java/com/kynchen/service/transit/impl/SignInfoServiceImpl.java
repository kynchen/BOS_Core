package com.kynchen.service.transit.impl;/*
* @author kynchen
* 
* @date 2018/8/16 11:23
*
* @version idea
*/

import bos.domain.transit.SignInfo;
import bos.domain.transit.TransitInfo;
import com.kynchen.dao.transit.SignInfoRepository;
import com.kynchen.dao.transit.TransitInfoRepository;
import com.kynchen.elastic.WayBillElasticRepository;
import com.kynchen.service.transit.SignInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SignInfoServiceImpl implements SignInfoService {

    @Autowired
    private SignInfoRepository signInfoRepository;
    @Autowired
    private TransitInfoRepository transitInfoRepository;
    @Autowired
    private WayBillElasticRepository wayBillElasticRepository;
    @Override
    public void save(String transitInfoId, SignInfo model) {

        signInfoRepository.save(model);

        TransitInfo transitInfo = transitInfoRepository.findOne(Integer.parseInt(transitInfoId));
        //关联签收信息
        transitInfo.setSignInfo(model);

        if(model.getSignType().equals("正常")){
            transitInfo.setStatus("正常签收");
            //修改运单状态
            transitInfo.getWayBill().setSignStatus(3);
        }else {
            transitInfo.setStatus("异常");
            transitInfo.getWayBill().setSignStatus(4);
        }
        //更新索引库
        wayBillElasticRepository.save(transitInfo.getWayBill());
    }
}
