package com.kynchen.service.base.impl;/*
* @author kynchen
* 
* @date 2018/8/1 11:19
*
* @version idea
*/

import com.kynchen.dao.base.SubAreaRepository;
import com.kynchen.domain.base.SubArea;
import com.kynchen.service.base.SubAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {

    @Autowired
    private SubAreaRepository subAreaRepository;

    /** 查询所有分区信息
     * @return
     */
    @Override
    public Page<SubArea> subArea_findAll(Specification<SubArea> specification, Pageable pageable) {
        return subAreaRepository.findAll(specification,pageable);
    }

    /** 添加分区信息
     * @param model
     */
    @Override
    public void save(SubArea model) {
        subAreaRepository.save(model);
    }

    @Override
    public List<SubArea> findAll() {
        return subAreaRepository.findAll();
    }
}
