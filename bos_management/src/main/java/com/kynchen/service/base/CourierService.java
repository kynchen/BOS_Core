package com.kynchen.service.base;/*
 * @author kynchen
 *
 * @date 2018/7/27 1:50
 *
 * @version idea
 */

import bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CourierService {
    void save(Courier courier);

    Page<Courier> findAllByPage(Specification<Courier> specification, Pageable pageable);

    void delBatch(String[] ids);

    void restoreBatch(String[] ids);

    List<Courier> findNoAssociation();


}
