package com.kynchen.service.base.impl;/*
* @author kynchen
* 
* @date 2018/7/24 19:47
*
* @version idea
*/

import com.kynchen.dao.base.StandardRepository;
import com.kynchen.domain.base.Standard;
import com.kynchen.service.base.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StandardServiceImpl implements StandardService {

    @Autowired
    private StandardRepository standardRepository;
    @Override
    public void standardSave(Standard standard) {
        standardRepository.save(standard);
    }

    @Override
    public Page<Standard> findAllByPage(Pageable pageable) {
        return standardRepository.findAll(pageable);
    }

    @Override
    public List<Standard> findAll() {
        return standardRepository.findAll();
    }
}
