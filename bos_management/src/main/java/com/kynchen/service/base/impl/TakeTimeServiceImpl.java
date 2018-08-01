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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {

    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public List<TakeTime> findAll() {
        return takeTimeRepository.findAll();
    }
}
