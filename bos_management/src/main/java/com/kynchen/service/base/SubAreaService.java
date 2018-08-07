package com.kynchen.service.base;/*
 * @author kynchen
 *
 * @date 2018/8/1 11:18
 *
 * @version idea
 */

import bos.domain.base.SubArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SubAreaService {
    Page<SubArea> subArea_findAll(Specification<SubArea> specification, Pageable pageable);

    void save(SubArea model);

    List<SubArea> findAll();

    void saveBatch(List<SubArea> subAreas);
}
