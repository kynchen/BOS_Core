package com.kynchen.service.base.impl;/*
* @author kynchen
* 
* @date 2018/7/28 19:38
*
* @version idea
*/

import com.kynchen.dao.base.CourierRepository;
import com.kynchen.dao.base.FixedAreaRepository;
import com.kynchen.dao.base.TakeTimeRepository;
import com.kynchen.domain.base.Courier;
import com.kynchen.domain.base.FixedArea;
import com.kynchen.domain.base.TakeTime;
import com.kynchen.service.base.FixedAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public void save(FixedArea model) {
        fixedAreaRepository.save(model);
    }

    @Override
    public Page<FixedArea> fixed_pageQuery(Specification<FixedArea> specification, Pageable pageable) {
        return fixedAreaRepository.findAll(specification,pageable);
    }

    @Override
    public List<FixedArea> findAll() {
        return fixedAreaRepository.findAll();
    }

    @Override
    public void associationCourierToFixedArea(FixedArea model, Integer courierId, Integer takeTimeId) {
        //获取定区对象
        FixedArea fixedArea = fixedAreaRepository.findOne(model.getId());
        //获取快递员对象
        Courier courier = courierRepository.findOne(courierId);
        //获取工作时间对象
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
        //由外键维护的那一方进行关联，级联保存
        fixedArea.getCouriers().add(courier);
        courier.setTakeTime(takeTime);
    }
}
