package com.kynchen.service.base.impl;/*
* @author kynchen
* 
* @date 2018/7/27 21:43
*
* @version idea
*/

import bos.domain.base.Area;
import com.kynchen.dao.base.AreaRepository;
import com.kynchen.service.base.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public void saveBatch(List<Area> areas) {
        areaRepository.save(areas);
    }

    @Override
    public Page<Area> pageQuery(Specification<Area> specification, Pageable pageable) {
        return areaRepository.findAll(specification,pageable);
    }

    /** 查询所有区域信息
     * @return
     */
    @Override
    public List<Area> area_finaAll() {
        return areaRepository.findAll();
    }
}
