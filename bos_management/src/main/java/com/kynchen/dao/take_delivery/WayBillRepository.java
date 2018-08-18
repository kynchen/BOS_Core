package com.kynchen.dao.take_delivery;/*
 * @author kynchen
 *
 * @date 2018/8/9 15:54
 *
 * @version idea
 */

import bos.domain.take_delivery.WayBill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WayBillRepository extends JpaRepository<WayBill,Integer> {
    WayBill findByWayBillNum(String wayBillNum);
}
