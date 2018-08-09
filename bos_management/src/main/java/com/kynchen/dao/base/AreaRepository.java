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

import java.util.List;

public interface AreaRepository extends JpaRepository<Area,String>,JpaSpecificationExecutor<Area> {
    List<Area> findByProvinceAndCityAndDistrict(String province, String city, String district);
}
