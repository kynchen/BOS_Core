package com.kynchen.service.transit;/*
 * @author kynchen
 *
 * @date 2018/8/16 11:22
 *
 * @version idea
 */

import bos.domain.transit.SignInfo;

public interface SignInfoService {
    void save(String transitInfoId, SignInfo model);
}
