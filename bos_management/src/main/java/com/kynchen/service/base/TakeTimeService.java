package com.kynchen.service.base;/*
 * @author kynchen
 *
 * @date 2018/8/1 9:07
 *
 * @version idea
 */

import bos.domain.base.TakeTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TakeTimeService {
    Page<TakeTime> findAll(Pageable pageable);

    void save(TakeTime model);

    List<TakeTime> findAllData();
}
