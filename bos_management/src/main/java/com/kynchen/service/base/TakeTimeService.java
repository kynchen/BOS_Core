package com.kynchen.service.base;/*
 * @author kynchen
 *
 * @date 2018/8/1 9:07
 *
 * @version idea
 */

import com.kynchen.domain.base.TakeTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TakeTimeService {
    Page<TakeTime> findAll(Pageable pageable);
}
