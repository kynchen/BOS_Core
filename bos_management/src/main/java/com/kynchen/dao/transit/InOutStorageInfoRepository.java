package com.kynchen.dao.transit;/*
 * @author kynchen
 *
 * @date 2018/8/15 21:04
 *
 * @version idea
 */

import bos.domain.transit.InOutStorageInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InOutStorageInfoRepository extends JpaRepository<InOutStorageInfo,Integer> {
}
