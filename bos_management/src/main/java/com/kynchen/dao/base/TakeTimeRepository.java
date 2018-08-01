package com.kynchen.dao.base;/*
 * @author kynchen
 *
 * @date 2018/8/1 9:08
 *
 * @version idea
 */

import com.kynchen.domain.base.TakeTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TakeTimeRepository extends JpaRepository<TakeTime,Integer> {

}
