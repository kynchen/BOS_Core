package com.kynchen.dao.base;/*
 * @author kynchen
 *
 * @date 2018/7/28 19:39
 *
 * @version idea
 */

import com.kynchen.domain.base.FixedArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FixedAreaRepository extends JpaRepository<FixedArea,String>,JpaSpecificationExecutor<FixedArea> {
}
