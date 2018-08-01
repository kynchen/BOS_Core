package com.kynchen.service.base;/*
 * @author kynchen
 *
 * @date 2018/7/28 19:38
 *
 * @version idea
 */

import com.kynchen.domain.base.FixedArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface FixedAreaService {
    void save(FixedArea model);

    Page<FixedArea> fixed_pageQuery(Specification<FixedArea> specification, Pageable pageable);

    void associationCourierToFixedArea(FixedArea model, Integer courierId, Integer takeTimeId);
}
