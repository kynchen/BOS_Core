package com.kynchen.dao.transit;/*
 * @author kynchen
 *
 * @date 2018/8/16 11:23
 *
 * @version idea
 */

import bos.domain.transit.SignInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignInfoRepository extends JpaRepository<SignInfo,Integer> {
}
