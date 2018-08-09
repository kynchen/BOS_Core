package com.kynchen.dao.take_delivery;/*
 * @author kynchen
 *
 * @date 2018/8/8 18:51
 *
 * @version idea
 */

import bos.domain.take_delivery.WorkBill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkBillRepository extends JpaRepository<WorkBill,Integer> {
}
