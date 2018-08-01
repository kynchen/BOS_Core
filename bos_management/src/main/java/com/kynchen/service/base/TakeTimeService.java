package com.kynchen.service.base;/*
 * @author kynchen
 *
 * @date 2018/8/1 9:07
 *
 * @version idea
 */

import com.kynchen.domain.base.TakeTime;

import java.util.List;

public interface TakeTimeService {
    List<TakeTime> findAll();
}
