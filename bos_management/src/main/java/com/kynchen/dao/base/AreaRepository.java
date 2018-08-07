package com.kynchen.dao.base;/*
 * @author kynchen
 *
 * @date 2018/7/27 21:44
 *
 * @version idea
 */

import bos.domain.base.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AreaRepository extends JpaRepository<Area,String>,JpaSpecificationExecutor<Area> {

}
