package com.kynchen.service.base.impl;/*
* @author kynchen
* 
* @date 2018/7/28 19:38
*
* @version idea
*/

import com.kynchen.dao.base.FixedAreaRepository;
import com.kynchen.domain.base.FixedArea;
import com.kynchen.service.base.FixedAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

    @Autowired
    private FixedAreaRepository fixedAreaRepository;

    @Override
    public void save(FixedArea model) {
        fixedAreaRepository.save(model);
    }

    @Override
    public Page<FixedArea> fixed_pageQuery(Specification<FixedArea> specification, Pageable pageable) {
        return fixedAreaRepository.findAll(specification,pageable);
    }
}
