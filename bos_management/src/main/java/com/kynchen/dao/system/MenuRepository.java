package com.kynchen.dao.system;/*
 * @author kynchen
 *
 * @date 2018/8/13 10:28
 *
 * @version idea
 */

import bos.domain.system.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu,Integer> {

    @Query("from Menu m inner join fetch m.roles r inner join fetch r.users u where u.id=?1 order by m.priority")
    List<Menu> findByUserId(int id);
}
