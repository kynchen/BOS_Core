package com.kynchen.service.base.impl;/*
* @author kynchen
* 
* @date 2018/8/1 9:07
*
* @version idea
*/

import bos.domain.base.TakeTime;
import com.kynchen.dao.base.TakeTimeRepository;
import com.kynchen.service.base.TakeTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {

    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public Page<TakeTime> findAll(Pageable pageable) {
        return takeTimeRepository.findAll(pageable);
    }

    @Override
    @CacheEvict("standard")
    public void save(TakeTime model) {
        takeTimeRepository.save(model);
    }

    @Override
    @Cacheable("standard")
    public List<TakeTime> findAllData() {
        return takeTimeRepository.findAll();
    }
}
