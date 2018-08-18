package com.kynchen.service.transit;/*
 * @author kynchen
 *
 * @date 2018/8/15 21:03
 *
 * @version idea
 */

import bos.domain.transit.InOutStorageInfo;

public interface InOutStorageInfoService {
    void save(String transitInfoId, InOutStorageInfo model);
}
