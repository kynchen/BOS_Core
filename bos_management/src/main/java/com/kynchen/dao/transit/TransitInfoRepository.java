package com.kynchen.dao.transit;/*
 * @author kynchen
 *
 * @date 2018/8/15 10:30
 *
 * @version idea
 */

import bos.domain.transit.TransitInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransitInfoRepository extends JpaRepository<TransitInfo,Integer> {
}
