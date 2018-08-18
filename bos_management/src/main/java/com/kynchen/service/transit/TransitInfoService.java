package com.kynchen.service.transit;/*
 * @author kynchen
 *
 * @date 2018/8/15 10:28
 *
 * @version idea
 */

import bos.domain.transit.TransitInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransitInfoService {
    void create(String wayBillIds);

    Page<TransitInfo> page_query(Pageable pageable);
}
