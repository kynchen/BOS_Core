package com.kynchen.service.transit;/*
 * @author kynchen
 *
 * @date 2018/8/15 21:50
 *
 * @version idea
 */

import bos.domain.transit.DeliveryInfo;

public interface DeliveryInfoService {
    void save(String transitInfoId, DeliveryInfo model);
}
