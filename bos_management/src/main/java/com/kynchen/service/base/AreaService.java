package com.kynchen.service.base;/*
 * @author kynchen
 *
 * @date 2018/7/27 21:43
 *
 * @version idea
 */

import bos.domain.base.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AreaService {
    void saveBatch(List<Area> areas);

    Page<Area> pageQuery(Specification<Area> specification, Pageable pageable);

    List<Area> area_finaAll();
}
