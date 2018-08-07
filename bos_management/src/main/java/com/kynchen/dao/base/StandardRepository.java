package com.kynchen.dao.base;/*
 * @author kynchen
 *
 * @date 2018/7/24 19:49
 *
 * @version idea
 */

import bos.domain.base.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StandardRepository extends JpaRepository<Standard,Integer> {

    public List<Standard> findByName(String name);

    @Query(value = "from Standard where name=?1",nativeQuery = false)
    public List<Standard> queryName(String name);

    @Query(value = "update Standard set minLength=?2 where id=?1")
    @Modifying
    public void updateMinLength(Integer id,Integer minLength);
}
