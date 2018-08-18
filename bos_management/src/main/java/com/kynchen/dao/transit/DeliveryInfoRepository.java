package com.kynchen.dao.transit;/*
 * @author kynchen
 *
 * @date 2018/8/15 21:51
 *
 * @version idea
 */

import bos.domain.transit.DeliveryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo,Integer> {
}
