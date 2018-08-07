package com.kynchen.service.take_delivery.impl;/*
* @author kynchen
* 
* @date 2018/8/3 22:01
*
* @version idea
*/

import bos.domain.page.PageBean;
import bos.domain.take_delivery.Promotion;
import com.kynchen.dao.take_delivery.PromotionRepository;
import com.kynchen.service.take_delivery.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public void save(Promotion model) {
        promotionRepository.save(model);
    }

    @Override
    public Page<Promotion> findAll(Pageable pageable) {
        return promotionRepository.findAll(pageable);
    }

    @Override
    public PageBean<Promotion> findPageData(int page, int rows) {
        Pageable pageable = new PageRequest(page-1,rows);
        Page<Promotion> pages = promotionRepository.findAll(pageable);
        PageBean<Promotion> pageBean = new PageBean<>();
        pageBean.setTotalCount(pages.getTotalElements());
        pageBean.setPageData(pages.getContent());
        return pageBean;
    }

    @Override
    public Promotion findById(Integer id) {
        return promotionRepository.findOne(id);
    }

    @Override
    public void updateDate(Date date) {
        promotionRepository.updateDate(date);

    }
}
