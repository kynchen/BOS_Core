package com.kynchen.service.base;/*
* @author kynchen
* 
* @date 2018/7/24 19:47
*
* @version idea
*/

import com.kynchen.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StandardService {
    void standardSave(Standard standard);

    Page<Standard> findAllByPage(Pageable pageable);

    List<Standard> findAll();
}
