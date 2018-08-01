package com.kynchen.service.base.impl;/*
* @author kynchen
* 
* @date 2018/8/1 9:07
*
* @version idea
*/

import com.kynchen.dao.base.TakeTimeRepository;
import com.kynchen.domain.base.TakeTime;
import com.kynchen.service.base.TakeTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {

    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public Page<TakeTime> findAll(Pageable pageable) {
        return takeTimeRepository.findAll(pageable);
    }
}
