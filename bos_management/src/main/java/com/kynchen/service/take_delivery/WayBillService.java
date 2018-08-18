package com.kynchen.service.take_delivery;/*
* @author kynchen
* 
* @date 2018/8/9 15:54
*
* @version idea
*/

import bos.domain.take_delivery.WayBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WayBillService {
    void save(WayBill model);

    Page<WayBill> pageQuery(WayBill wayBill, Pageable pageable);

    WayBill findByWayBillNum(String wayBillNum);

    void syncIndex();

    Page<WayBill> page_query(Pageable pageable);

    List<WayBill> findAllWayBills(WayBill model);
}
