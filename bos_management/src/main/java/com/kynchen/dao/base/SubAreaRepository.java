package com.kynchen.dao.base;/*
 * @author kynchen
 *
 * @date 2018/8/1 11:19
 *
 * @version idea
 */

import com.kynchen.domain.base.SubArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubAreaRepository extends JpaRepository<SubArea,String>,JpaSpecificationExecutor<SubArea> {
}
