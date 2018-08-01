package com.kynchen.service.base.impl;/*
* @author kynchen
* 
* @date 2018/7/27 1:51
*
* @version idea
*/

import com.kynchen.dao.base.CourierRepository;
import com.kynchen.domain.base.Courier;
import com.kynchen.service.base.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CourierServiceImpl implements CourierService {

    @Autowired
    private CourierRepository courierRepository;
    @Override
    public void save(Courier courier) {
        courierRepository.save(courier);
    }


    @Override
    public Page<Courier> findAllByPage(Specification<Courier> specification,Pageable pageable) {
        return courierRepository.findAll(specification,pageable);
    }

    @Override
    public void delBatch(String[] ids) {
        for (String s:ids
             ) {
            Integer id = Integer.parseInt(s);
            courierRepository.updateDeltag(id);
        }
    }

    @Override
    public void restoreBatch(String[] ids) {
        for (String s:ids
             ) {
            Integer id = Integer.parseInt(s);
            courierRepository.restoreDeltag(id);
        }
    }

    @Override
    public List<Courier> findNoAssociation() {
        Specification<Courier> specification = new Specification<Courier>() {
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.isEmpty(root.get("fixedAreas").as(Set.class));
                return predicate;
            }
        };
        return courierRepository.findAll(specification);
    }
}
