package com.kynchen.service.transit.impl;/*
* @author kynchen
* 
* @date 2018/8/15 21:04
*
* @version idea
*/

import bos.domain.transit.InOutStorageInfo;
import bos.domain.transit.TransitInfo;
import com.kynchen.dao.transit.InOutStorageInfoRepository;
import com.kynchen.dao.transit.TransitInfoRepository;
import com.kynchen.service.transit.InOutStorageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InOutStorageInfoServiceImpl implements InOutStorageInfoService {

    @Autowired
    private InOutStorageInfoRepository inOutStorageInfoRepository;
    @Autowired
    private TransitInfoRepository transitInfoRepository;
    @Override
    public void save(String transitInfoId, InOutStorageInfo model) {
        inOutStorageInfoRepository.save(model);

        TransitInfo transitInfo = transitInfoRepository.findOne(Integer.parseInt(transitInfoId));
        //关联出入库信息
        transitInfo.getInOutStorageInfos().add(model);
        //修改运输状态
        if(model.getOperation().equals("到达网点")){
            transitInfo.setStatus("到达网点");
            //更新网点
            transitInfo.setOutletAddress(model.getAddress());
        }
    }
}
